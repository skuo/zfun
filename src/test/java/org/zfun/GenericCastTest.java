package org.zfun;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GenericCastTest {

    @Test
    /*
     * A simple test to illustrate how to specify a type for a generic method.
     */
    public void testCast() {
        Integer i = new Integer(0);
        Number n = i;
        Integer newI = GenericCast.<Integer>cast(n);
        assertTrue(newI instanceof Integer);

        Long l = new Long(0);
        n = l;
        // This statement is not compilable because of type mismatch
        // Long newL = GenericCast.<Integer>cast(n);
        Long newL = GenericCast.<Long>cast(n);
        assertTrue(newL instanceof Long);
    }
}
