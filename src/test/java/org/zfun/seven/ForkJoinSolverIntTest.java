package org.zfun.seven;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinSolverIntTest {

    public static void main(String[] args) {
        // create 2,000,000 random integers.
        int[] list = new int[20];
        Random generator = new Random(19580427);
        for (int i = 0; i < list.length; i++) {
            list[i] = generator.nextInt(500000);
        }

        // Set up ForkJoinPool and start running
        int nProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("nProcessors: " + nProcessors);
        ForkJoinSolver mfj = new ForkJoinSolver(list, 0);
        ForkJoinPool pool = new ForkJoinPool(nProcessors);
        pool.invoke(mfj);
        long result = mfj.getResult();
        System.out.println("ForkJoinSolver. Result: " + result);
        long sum = 0;
        // Check if the result was ok
        for (int i = 0; i < list.length; i++) {
            sum += list[i];
        }
        System.out.println("Done. Result: " + sum);
    }
}
