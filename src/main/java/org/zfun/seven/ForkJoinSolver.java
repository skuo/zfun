package org.zfun.seven;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class ForkJoinSolver extends RecursiveAction {
    private static final long serialVersionUID = -6847078035576037181L;
    private int[] list;
    public long result;
    private int level;

    public ForkJoinSolver(int[] array, int level) {
        this.list = array;
        this.level = level;
    }

    @Override
    protected void compute() {
        if (list.length == 1) {
            result = list[0];
        } else {
            int midpoint = list.length / 2;
            int[] l1 = Arrays.copyOfRange(list, 0, midpoint);
            int[] l2 = Arrays.copyOfRange(list, midpoint, list.length);
            ForkJoinSolver s1 = new ForkJoinSolver(l1, level+1);
            ForkJoinSolver s2 = new ForkJoinSolver(l2, level+1);
            invokeAll(s1, s2);
            result = s1.result + s2.result;
            System.out.format("[%d] result=%d\n", level, result);
        }
    }
    
    public long getResult() {
        return result;
    }
}
