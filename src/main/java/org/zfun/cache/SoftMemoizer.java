package org.zfun.cache;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class SoftMemoizer<A, V> implements Computable<A, V> {
    private final ConcurrentMap<A, Future<SoftReference<V>>> cache = new ConcurrentHashMap<A, Future<SoftReference<V>>>();
    private final Computable<A, V> c;

    public SoftMemoizer(Computable<A, V> c) {
        this.c = c;
    }

    /**
     * Loop twice and return null if failed to compute.
     */
    public V compute(final A arg) throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            Future<SoftReference<V>> f = cache.get(arg);
            if (f == null) {
                Callable<SoftReference<V>> eval = new Callable<SoftReference<V>>() {
                    public SoftReference<V> call() throws InterruptedException {
                        return new SoftReference<V>(c.compute(arg));
                    }
                };
                FutureTask<SoftReference<V>> ft = new FutureTask<SoftReference<V>>(eval);
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                V v = f.get().get();
                if (v == null) {
                    // if it is garbage-collected, remove it from cache loop back to recompute it
                    cache.remove(arg, f);
                } else {
                    return v;
                }

            } catch (CancellationException e) {
                cache.remove(arg, f);
            } catch (ExecutionException e) {
                throw LaunderThrowable.launderThrowable(e.getCause());
            }
        }
        return null;
    }

    @Override
    public V compute(A arg, V last) throws InterruptedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isError() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public WeakReference<V> computeWeakReference(A arg) throws InterruptedException {
        // TODO Auto-generated method stub
        return null;
    }
}