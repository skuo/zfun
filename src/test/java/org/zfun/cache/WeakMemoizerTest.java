package org.zfun.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.ref.WeakReference;

import org.junit.Before;
import org.junit.Test;

public class WeakMemoizerTest {

    private IntegerComputable c;
    private WeakMemoizer<String, Integer> cache;

    @Before
    public void setup() {
        c = new IntegerComputable();
        cache = new WeakMemoizer<String, Integer>(c);
    }

    @Test
    public void testWeakMemoizer() throws InterruptedException {
        assertCompute(Integer.valueOf(1), "a");
        assertCompute(Integer.valueOf(1), "b");
        assertCompute(Integer.valueOf(1), "a");

        // Call gc to reclaim WeakReference
        System.gc();
        assertCompute(Integer.valueOf(2), "a");
    }

    /*
     * A helper method where i, the reference to the Integer object pointed to by WeakReference, is 
     * collectible after method returns.
     */
    private void assertCompute(Integer expectedInt, String arg) throws InterruptedException {
        Integer i = cache.compute("a");
        assertEquals(Integer.valueOf(expectedInt), i);
    }

    @Test
    /**
     * Weak reference is reclaimed after 1 gc.
     */
    public void testWeakReference() {
        Integer weakInt = new Integer(10);
        WeakReference<Integer> weakReference = new WeakReference<Integer>(weakInt);

        weakInt = null;
        int i = 0;
        while (weakReference.get() != null) {
            System.out.println("Looping..." + i++);
            System.gc();
        }
        System.out.println("Weak reference collected after " + i + " loops");
        assertNull(weakReference.get());
    }
}
