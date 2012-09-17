package org.zfun.seven;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ApplyerIntTest {

    public static void main(String[] args) {
        // create 2,000,000 random integers.
        double[] list = new double[1000000];
        Random generator = new Random(19580427);
        for (int i = 0; i < list.length; i++) {
            list[i] = generator.nextInt(500000);
        }

        // Set up ForkJoinPool and start running
        int nProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("nProcessors: " + nProcessors);
        Applyer a = new Applyer(list, 0, list.length, null);
        ForkJoinPool pool = new ForkJoinPool(nProcessors);
        pool.invoke(a);
        double result = a.getResult();
        System.out.println("Applyer result: " + result);
    }
}
