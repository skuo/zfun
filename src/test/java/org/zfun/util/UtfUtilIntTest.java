package org.zfun.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtfUtilIntTest {

    @Test
    public void testStrInUtfRepresentation() {
        String s = UtfUtil.StrInUtfRepresentation("Nuñez\u0123\u67e5\u770b\u5168\u90e8");
        assertEquals("\\u004e\\u0075\\u00f1\\u0065\\u007a\\u0123\\u67e5\\u770b\\u5168\\u90e8", s);
    }
    
    @Test
    public void testUtfRepresentation2Str() {
        String s = UtfUtil.utfRepresentation2Str("\\u004e\\u0075\\u00f1\\u0065\\u007a\\u0123\\u67e5\\u770b\\u5168\\u90e8");
        assertEquals("Nuñez\u0123\u67e5\u770b\u5168\u90e8", s);
    }
}
