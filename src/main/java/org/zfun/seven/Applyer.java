package org.zfun.seven;

import java.util.concurrent.RecursiveAction;

public class Applyer extends RecursiveAction {
    private static final long serialVersionUID = 5964120902421566240L;
    private final double[] array;
    private final int lo;
    private final int hi;
    private double result;
    private Applyer next; // keep track of right-hand-size tasks

    Applyer(double[] array, int lo, int hi, Applyer next) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
        this.next = next;
        System.out.format("Constructor lo=%d, hi=%d%n", lo, hi);
    }

    public double getResult() {
        return result;
    }

    private double atLeaf(int l, int h) {
        double sum = 0;
        // perform leftmost base step
        for (int i = l; i < h; ++i)
            sum += array[i] * array[i];
        return sum;
    }

    protected void compute() {
        int l = lo;
        int h = hi;
        Applyer right = null;
        while (h - l > 1 && getSurplusQueuedTaskCount() <= 3) {
            int mid = (l + h) >>> 1;
            right = new Applyer(array, mid, h, right);
            right.fork();
            h = mid;
        }

        double sum = atLeaf(l, h);
        while (right != null) {
            if (right.tryUnfork()) {
                // directly calculate if not stolen
                sum += right.atLeaf(right.lo, right.hi);
                System.out.format("Direct calculation for right.lo=%d, right.hi=%d\n", right.lo, right.hi);
            }
            else {
                // wait until right is done
                right.join();
                System.out.format("Join result for right.lo=%d, right.hi=%d\n", right.lo, right.hi);
                sum += right.result;
            }
            right = right.next;
        }
        result = sum;
    }
}
