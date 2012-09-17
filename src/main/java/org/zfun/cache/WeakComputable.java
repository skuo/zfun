package org.zfun.cache;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;

/**
 * User: skuo Date: Apr 5, 2012
 * 
 */
public class WeakComputable<A, V> {

    private Computable<A, V> cTemp;
    private A arg;
    private WeakReference<Computable<A, V>> weakC;

    /**
     * Create a reference cTemp for input c, a WeakReference for cTemp and set
     * cTemp to null so that WeakReference can be reclaimed.
     * 
     * @param c
     */
    public WeakComputable(Computable<A, V> c, A arg) {
        this.cTemp = c;
        this.weakC = new WeakReference<Computable<A, V>>(cTemp);
        this.arg = arg;
        this.cTemp = null;
    }

    public V compute() throws InterruptedException {
        if (getWeakC().get() == null)
            return null;
        else
            return weakC.get().compute(arg);
    }

    /**
     * This actually does not create a WeakReference that is reclaimed by GC.
     * @return
     * @throws InterruptedException
     */
    public WeakReference<V> computeWeakResult() throws InterruptedException {
        V result = compute();
        if (result == null)
            return null;
        else {
            WeakReference<V> weakRef = new WeakReference<V>(result);
            result = null;
            return weakRef;
        }
    }

    /**
     * If Computable<A,V>.computeWeakReference() is done correctly, then the returned
     * WeakReference is reclaimed by GC.
     * @return
     * @throws InterruptedException
     */
    public WeakReference<V> computeWeakReference() throws InterruptedException {
        if (getWeakC().get() == null)
            return null;
        else
            return weakC.get().computeWeakReference(arg);
    }

    public WeakReference<Computable<A, V>> getWeakC() {
        return weakC;
    }

    public void setWeakC(WeakReference<Computable<A, V>> weakC) {
        this.weakC = weakC;
    }

    public Callable<WeakReference<V>> getCallable() {
        Callable<WeakReference<V>> eval = new Callable<WeakReference<V>>() {
            public WeakReference<V> call() throws InterruptedException {
                return computeWeakReference();
            }
        };
        return eval;
    }
}
