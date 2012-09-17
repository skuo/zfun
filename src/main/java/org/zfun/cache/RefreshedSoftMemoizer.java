package org.zfun.cache;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Future;

public class RefreshedSoftMemoizer<A, V extends ComputedResult> {

    private class TimedV<D extends ComputedResult> {
        private final D value;
        private long born = System.currentTimeMillis();
        private boolean isExpired = false;
        
        public TimedV(D value) {
            this(value, false);
        }
        
        public TimedV(D value, boolean isExpired) {
            this.value = value;
            this.isExpired = isExpired;
        }
    }
    
    private final ConcurrentMap<A, Future<SoftReference<TimedV<V>>>> cache = new ConcurrentHashMap<A, Future<SoftReference<TimedV<V>>>>();
    private final ConcurrentMap<A, SoftReference<V>> backupMap = new ConcurrentHashMap<A, SoftReference<V>>();
    
}
