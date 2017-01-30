package org.zfun.eight;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

public class CompletableFutureTest {

    @Test
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
    
    public int longRunningTask(int num) {
        try {
            // simulate long running task
            Thread.sleep(5000); // 5000 ms
        } catch (InterruptedException ie) {
            ;
        }
        return num;
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
}
