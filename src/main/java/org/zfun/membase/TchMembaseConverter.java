package org.zfun.membase;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TchMembaseConverter {

    private TchReader tchReader;
    private Integer errorLimit;
    private Integer noOfRecords;
    private int expiration;
    private MembaseWriter membaseWriter;
    private MembaseReader membaseReader;

    public TchMembaseConverter(String urls, String bucketName, String password) throws IOException {
        this.membaseWriter = new MembaseWriter(urls, bucketName, password);
        this.membaseReader = new MembaseReader(urls, bucketName, password);
    }

    public TchReader getTchReader() {
        return tchReader;
    }

    public void setTchReader(TchReader tchReader) {
        this.tchReader = tchReader;
    }

    public Integer getErrorLimit() {
        return errorLimit;
    }

    public void setErrorLimit(Integer errorLimit) {
        this.errorLimit = errorLimit;
    }

    public Integer getNoOfRecords() {
        return noOfRecords;
    }

    public void setNoOfRecords(Integer noOfRecords) {
        this.noOfRecords = noOfRecords;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public void tchToMembase() {
        HashMap<String, Object> keysAndValues;
        HashMap<String, Object> keysAndValuesWithErrors = new HashMap<String, Object>();
        long count = 0;
        while (!(keysAndValues = tchReader.readNextBlock()).isEmpty()) {
            final HashMap<String, Object> errors = membaseWriter.write(keysAndValues, expiration);
            final HashMap<Object, Object> values = membaseReader.read(keysAndValues);
            System.out.println("read " + values.size() + " values");
            keysAndValuesWithErrors.putAll(errors);
            if (keysAndValuesWithErrors.size() > errorLimit) {
                System.out.println("too many errs quitting");
                break;
            }
            count = count + keysAndValues.size();
            System.out.format("Processed %d records\n", count);
            if (count >= noOfRecords)
                break;
        }
        // retry errors
        membaseWriter.write(keysAndValuesWithErrors, expiration);
    }

    public void close() {
        tchReader.closeTch();
        membaseWriter.destroy();
        membaseReader.destroy();
    }
    
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("tch-membase-converter-all.xml");
        final TchMembaseConverter tchMembaseConverter = (TchMembaseConverter) context.getBean("tchMembaseConverter");
        long start = System.currentTimeMillis();
        tchMembaseConverter.tchToMembase();
        long totalTime = (System.currentTimeMillis() - start)/1000;
        System.out.format("total time = %d (s), noOfRecords = %d, noOfOperations = %d, %d ops/sec %n", totalTime,
                tchMembaseConverter.getNoOfRecords(), 2 * tchMembaseConverter.getNoOfRecords(),
                2 * tchMembaseConverter.getNoOfRecords() / totalTime);
        tchMembaseConverter.close();
        context.close();
    }

}
