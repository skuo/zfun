package org.zfun.cache;

import java.lang.ref.WeakReference;

public interface Computable<A, V> {
    /**
     * Return V.
     * @param arg
     * @return
     * @throws InterruptedException
     */
    public V compute(A arg) throws InterruptedException;
    
    /**
     * Expensive computation.
     * @param arg
     * @param last last result for this arg
     * @return
     * @throws InterruptedException
     */
    public V compute(A arg, V last) throws InterruptedException;

    /**
     * Return WeakReference<V> that is reclaimed after GC.
     * @param arg
     * @return
     * @throws InterruptedException
     */
    public WeakReference<V> computeWeakReference(A arg) throws InterruptedException;
    
    public boolean isError();

}
