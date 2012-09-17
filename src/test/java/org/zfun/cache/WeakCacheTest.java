package org.zfun.cache;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class WeakCacheTest {
    
    private WeakCache<String, Integer> cache;

    @Before
    public void setup() {
        IntegerComputable c = new IntegerComputable();
        cache = new WeakCache<String, Integer>(c);
        c = null;
    }
    
    @Test
    public void testWeakCache() throws InterruptedException {
        Integer i = cache.compute("a");
        assertEquals(Integer.valueOf(1), i);
        i = cache.compute("b");
        assertEquals(Integer.valueOf(1), i);
        i = cache.compute("a");
        assertEquals(Integer.valueOf(1), i);

        // set i to null so that gc would be able to reclaim WeakReference
        i = null;
        System.gc();
        i = cache.compute("a");
        assertEquals(Integer.valueOf(2), i);  // second compute("a") is called
    }
    
    @Test
    /*
     * Do assertion login in a method so that i does not have to be explicitly set to null.
     */
    public void testWeakCache2() throws InterruptedException {
        assertCompute(Integer.valueOf(1), "a");
        assertCompute(Integer.valueOf(1), "b");
        assertCompute(Integer.valueOf(1), "a");

        // Call gc to reclaim WeakReference
        System.gc();
        assertCompute(Integer.valueOf(2), "a");
    }
    
    private void assertCompute(Integer expectedInt, String arg)
            throws InterruptedException {
        Integer i = cache.compute("a");
        assertEquals(Integer.valueOf(expectedInt), i);        
    }
    
}
