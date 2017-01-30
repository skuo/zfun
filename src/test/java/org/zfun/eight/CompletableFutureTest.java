package org.zfun.eight;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.junit.Test;

public class CompletableFutureTest {
    private Random random = new Random();

    //@Test
    public void testCompletableFuture() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> futureCount = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            return 10;
        });
        CompletableFuture<Void> cf2 = CompletableFuture.runAsync(new LongTaskRunnable(3,3000));
        // Doing something else
        System.out.println("Print this statement while waiting for CompletableFuture");
        // wait until CompletableFutures complete
        CompletableFuture.allOf(futureCount, cf2).join();
        // All CompletableFutures should be done at this point
        if (futureCount.isDone()) {
            int count = (int) futureCount.get();
            System.out.println("Success: futureCount, count=" + count);
        } else {
            System.out.println("ERROR: futureCount is not done");
        }
        if (cf2.isDone())
            System.out.println("Success: LongTaskRunnable is done");
        else 
            System.out.println("ERROR: LongTaskRunnable is not done");
    }
    
    private class LongTaskRunnable implements Runnable {
        private int count;
        private int waitInMs; // wait time in milliseconds
        
        public LongTaskRunnable(int count, int waitInMs) {
            this.count = count;
            this.waitInMs = waitInMs;
        }
        
        public void run() {
            try {
                // simulate long running task
                Thread.sleep(waitInMs); 
            } catch (InterruptedException ie) {
                ;
            }
            System.out.println("LongTaskRunnable count=" + count);
        }
    }
    
    @Test
    public void testSites() throws InterruptedException, ExecutionException {
        System.out.println("in testSites()");
        List<String> sites = Arrays.asList("site1", "site2", "site3", "site4");
        List<CompletableFuture<String>> completableFutures = sites.stream()
                .map(site -> CompletableFuture.supplyAsync(() -> downloadSite(site,5000)))
                .collect(Collectors.<CompletableFuture<String>>toList());
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()])).join();
        // loop through all completableFutures
        for (CompletableFuture<String> cf : completableFutures) {
            if (cf.isDone()) {
                System.out.println("[Success] " + cf.get());
            } else {
                System.out.println("[ERROR] is not done");
            }
        }
    }
    
    private String downloadSite(final String site, int waitInMsMax) {
        int randomInt = random.nextInt((waitInMsMax-1)+1)+1;
        String res = site + ": waited " + randomInt + "(ms)";
        try {
            // simulate long running task
            Thread.sleep(randomInt); 
        } catch (InterruptedException ie) {
            ;
        }
        System.out.println(res);
        return res;
    }
}
