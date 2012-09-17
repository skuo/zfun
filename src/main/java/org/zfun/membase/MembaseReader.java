package org.zfun.membase;

import com.couchbase.client.CouchbaseClient;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.GetFuture;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Future;

public class MembaseReader {
    public static final int MILLION = 1000000;
    MemcachedClient memcachedClient;


    public MembaseReader(String commaSeperatedUris, String bucketName, String password) throws IOException {
        memcachedClient = new CouchbaseClient(AddrUtil.getAddresses(commaSeperatedUris), bucketName, password);
    }

    public HashMap<Object, Object> read(HashMap<String, Object> keysAndvalues) {
        if (keysAndvalues.size() > MILLION) throw new IllegalArgumentException("size should be less than million");
        List<GetFuture<Object>> getFutures = readEntries(keysAndvalues);
        return collectValues(getFutures);
    }

    public Object read(String key) {
        return memcachedClient.get(key);
    }

    private HashMap<Object, Object> collectValues(List<GetFuture<Object>> getFutures) {
        HashMap<Object, Object> values = new HashMap<Object, Object>();
        StringBuilder errorMessage = new StringBuilder();
        for (GetFuture<Object> next : getFutures) {
            if (next == null) continue;
            final Object result = getResult(next);
            if (result == null) continue;
            if (!next.getStatus().isSuccess())
                errorMessage.append(next.getStatus().getMessage());
            values.put(result, result);
        }
        System.out.println("Read errors " + errorMessage);
        return values;
    }

    private List<GetFuture<Object>> readEntries(HashMap<String, Object> keysAndvalues) {
        final Set<Map.Entry<String, Object>> entries = keysAndvalues.entrySet();
        List<GetFuture<Object>> getFutures = new ArrayList<GetFuture<Object>>();
        for (Map.Entry<String, Object> entry : entries) {
            final GetFuture<Object> result = get(entry);
            getFutures.add(result);
        }
        return getFutures;
    }

    private Object getResult(Future<Object> next) {
        try {
            return next.get();
        } catch (InterruptedException e) {
        } catch (Exception e) {
            System.out.println("error in writing: during get " + e.getMessage());
        }
        return false;
    }

    private GetFuture<Object> get(Map.Entry<String, Object> entry) {
        try {
            final Object value = entry.getValue();
            if (value == null) return null;
            return memcachedClient.asyncGet(entry.getKey());
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
