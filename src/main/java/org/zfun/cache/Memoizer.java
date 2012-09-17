package org.zfun.cache;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Memoizer<A, V> implements Computable<A, V>  
{  
    private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();  
    private final Computable<A, V> c;  
  
    public Memoizer(Computable<A, V> c)  
    {  
        this.c = c;  
    }  
  
    public V compute(final A arg) throws InterruptedException  
    {  
        while (true) {  
            Future<V> f = cache.get(arg);  
            if (f == null) {  
                Callable<V> eval = new Callable<V>() {  
                    public V call() throws InterruptedException {  
                        return c.compute(arg);  
                    }  
                };  
                FutureTask<V> ft = new FutureTask<V>(eval);  
                f = cache.putIfAbsent(arg, ft);  
                if (f == null) {  
                    f = ft;  
                    ft.run();  
                }  
            }  
            try {  
                return f.get();  
            } catch (CancellationException e) {  
                cache.remove(arg, f);  
            } catch (ExecutionException e) {  
                LaunderThrowable.launderThrowable(e);  
            }  
        }  
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