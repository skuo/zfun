package org.zfun.cache;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: skuo
 * Date: Apr 3, 2012
 */
public class IntegerComputable implements Computable<String, Integer> {

    private ConcurrentMap<String, AtomicInteger> map = new ConcurrentHashMap<String, AtomicInteger>();
    @Override
    public Integer compute(String arg) throws InterruptedException {
        AtomicInteger count = map.get(arg);
        if (count == null) {
            count = new AtomicInteger(0);
            // Set atomic integer to 0 if there is no value associated with key (=arg)
            map.putIfAbsent(arg, count);
            // Get atomic integer.  It could be 0 as set in previous line or another set by other thread
            count = map.get(arg);
        }
        return count.incrementAndGet();
    }

    public WeakReference<Integer> computeWeakReference(String arg) throws InterruptedException {
        Integer i = new Integer(compute(arg));
        WeakReference<Integer> wr = new WeakReference<Integer>(i);
        i = null;
        return wr;
    }
    
    @Override
    public Integer compute(String arg, Integer last) throws InterruptedException {
        return null;
    }

    @Override
    public boolean isError() {
        return false;
    }

}
