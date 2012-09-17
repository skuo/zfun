package org.zfun.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.ref.SoftReference;

import org.junit.Test;

public class SoftMemoizerTest {

    @Test
    public void testSoftMemoizer() throws InterruptedException {
        IntegerComputable c = new IntegerComputable();
        SoftMemoizer<String, Integer> cache = new SoftMemoizer<String, Integer>(c);
        
        Integer i = cache.compute("a");
        assertEquals(Integer.valueOf(1), i);
        i = cache.compute("b");
        assertEquals(Integer.valueOf(1), i);
        
        // Forced Garbage Collection will only collect SoftReference if available memory is little
        // Thus, gc() will not reclaim SoftReference for "a"
        System.gc();
        i = cache.compute("a");
        assertEquals(Integer.valueOf(1), i);
    }

    //@Test
    /**
     * Soft reference does not get GC'ed until the available memory is very small.
     * This test does not reclaim soft reference even after thousands of loops.
     */
    public void testSoftReference() {
        Integer softInt = new Integer(10);
        SoftReference<Integer> softReference = new SoftReference<Integer>(softInt);

        softInt = null;
        int i = 0;
        while (softReference.get() != null) {
            System.out.println("Looping..." + i++);
            @SuppressWarnings("unused")
            String[] generateOutOfMemoryStr = new String[999999];
            System.gc();
        }
        System.out.println("Soft reference collected after " + i + " loops");
        assertNull(softReference.get());
    }
}
