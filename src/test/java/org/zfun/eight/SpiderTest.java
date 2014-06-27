package org.zfun.eight;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SpiderTest {

	@Test
    public void testSampleFormula() {
		Spider spider = new Spider();
        int i = 100;
        assertEquals(100, spider.calculate(i), 0.0001);
        assertEquals(4, spider.sqrt(16), 0.0001);
    }

}
