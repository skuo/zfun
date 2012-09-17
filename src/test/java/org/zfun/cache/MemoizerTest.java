package org.zfun.cache;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MemoizerTest {

    @Test
    public void testMemoizer() throws InterruptedException {
        IntegerComputable c = new IntegerComputable();
        Memoizer<String, Integer> cache = new Memoizer<String, Integer>(c);
        
        Integer i = cache.compute("a");
        assertEquals(Integer.valueOf(1), i);
        i = cache.compute("b");
        assertEquals(Integer.valueOf(1), i);
        i = cache.compute("a");
        assertEquals(Integer.valueOf(1), i);
    }
}
