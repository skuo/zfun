package org.zfun.cache;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.lang.ref.WeakReference;

import org.junit.Test;

/**
 * User: skuo Date: Apr 5, 2012
 */
public class WeakComputableTest {
    @Test
    /**
     * Weak reference is reclaimed after 1 gc.
     */
    public void testWeakComputable() throws InterruptedException {
        IntegerComputable c = new IntegerComputable();
        WeakComputable<String, Integer> weakComputable = new WeakComputable<String, Integer>(c, "a");
        c = null;

        int i = 0;
        while (weakComputable.getWeakC().get() != null) {
            System.out.println("Looping..." + i++);
            System.out.println("result=" + weakComputable.compute());
            System.gc();
        }
        System.out.println("Weak reference collected after " + i + " loops");
        assertEquals(1, i);
        assertNull(weakComputable.getWeakC().get());
    }

    @Test
    /**
     * Weak reference is reclaimed after 1 gc.
     */
    public void testWeakResult() throws InterruptedException {
        IntegerComputable c = new IntegerComputable();
        WeakComputable<String, Integer> weakComputable = new WeakComputable<String, Integer>(c, "a");
        c = null;
        
        WeakReference<Integer> weakInt = weakComputable.computeWeakResult();
        assertEquals(1, weakInt.get().intValue());
        WeakReference<Integer> weakRefInt = weakComputable.computeWeakReference();
        assertEquals(2, weakRefInt.get().intValue());
        //
        System.gc();
        assertNull(weakComputable.getWeakC().get());
        assertNull(weakComputable.computeWeakResult());
        assertNull(weakRefInt.get());
        assertNotNull(weakInt.get());
    }
}
