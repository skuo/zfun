package org.zfun.membase;

import com.couchbase.client.CouchbaseClient;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import net.spy.memcached.ops.OperationStatus;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class MembaseWriter {
    public static final int MILLION = 1000000;
    MemcachedClient memcachedClient;

    public MembaseWriter(String commaSeperatedUris, String bucketName, String password) throws IOException {
        memcachedClient = new CouchbaseClient(AddrUtil.getAddresses(commaSeperatedUris), bucketName, password);
    }

    /**
     * Takes in keys and values and returns keys and values that erred
     * Assumes the hashmap is of reasonable size  < 1m
     *
     * @param keysAndvalues
     * @param expiration
     * @return
     */
    public HashMap<String, Object> write(HashMap<String, Object> keysAndvalues, int expiration) {
        if (keysAndvalues.size() > MILLION) throw new IllegalArgumentException("size should be less than million");
        List<OperationFuture<Boolean>> operationFutures = writeEntries(keysAndvalues, expiration);
        return collectErrorValues(operationFutures, keysAndvalues);
    }

    public List<OperationFuture<Boolean>> writeAsync(HashMap<String, Object> keysAndvalues, int expiration) {
        if (keysAndvalues.size() > MILLION) throw new IllegalArgumentException("size should be less than million");
        return writeEntries(keysAndvalues, expiration);
    }

    public HashMap<String, Object> collectErrorValuesAsync(List<OperationFuture<Boolean>> operationFutures, HashMap<String, Object> keysAndvalues, int timeout) {
        HashMap<String, Object> errors = new HashMap<String, Object>();
        StringBuilder errorStr = new StringBuilder();
        for (OperationFuture<Boolean> next : operationFutures) {
            if (next == null) continue;
            final Boolean result = getResult(next,timeout);
            final OperationStatus status = next.getStatus();
            if (result == null || !status.isSuccess()) {
                final String key = next.getKey();
                errors.put(key, keysAndvalues.get(key));
                if (status != null)
                    errorStr.append(status.getMessage()).append("\n");
            }
        }
        return errors;
    }

    private List<OperationFuture<Boolean>> writeEntries(HashMap<String, Object> keysAndvalues, int expiration) {
        final Set<Map.Entry<String, Object>> entries = keysAndvalues.entrySet();
        List<OperationFuture<Boolean>> operationFutures = new ArrayList<OperationFuture<Boolean>>();
        for (Map.Entry<String, Object> entry : entries) {
            final OperationFuture<Boolean> result = set(entry, expiration);
            operationFutures.add(result);
        }
        return operationFutures;
    }

    private HashMap<String, Object> collectErrorValues(List<OperationFuture<Boolean>> operationFutures, HashMap<String, Object> keysAndvalues) {
        HashMap<String, Object> errors = new HashMap<String, Object>();
        StringBuilder errorStr = new StringBuilder();
        for (OperationFuture<Boolean> next : operationFutures) {
            if (next == null) continue;
            final Boolean result = getResult(next);
            final OperationStatus status = next.getStatus();
            if (result == null || !status.isSuccess()) {
                final String key = next.getKey();
                errors.put(key, keysAndvalues.get(key));
                if (status != null)
                    errorStr.append(status.getMessage()).append("\n");
            }
        }
        return errors;
    }

    private Boolean getResult(OperationFuture<Boolean> next) {
        try {
            return next.get();
        } catch (InterruptedException e) {

        } catch (Exception e) {
            System.out.println("error in writing: during get " + e.getMessage());
        }
        return false;
    }

    private Boolean getResult(OperationFuture<Boolean> next, int timeout) {
        try {
            return next.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
        } catch (Exception e) {
            System.out.println("error in writing: during get " + e.getMessage());
        }
        return false;
    }

    private OperationFuture<Boolean> set(Map.Entry<String, Object> entry, int expiration) {
        try {
            final Object value = entry.getValue();
            if (value == null) return null;
            return memcachedClient.set(entry.getKey(), expiration, value);
        } catch (Exception e) {
            System.out.println("error in writing " + e.getMessage());
        }
        return null;
    }

    @PreDestroy
    public void destroy() {
        System.out.println("shutting down membase client");
        memcachedClient.shutdown();
    }
}
