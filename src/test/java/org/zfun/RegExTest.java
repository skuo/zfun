package org.zfun;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * User: skuo
 * Date: Jun 6, 2011
 */
public class RegExTest {
    @Test
    public void testRemoveAllNonDigit() {
        String str = "(818) 123-4567";
        str = str.replaceAll( "[^\\d]", "" );
        assertEquals("8181234567", str);

        str = "818_ 123-4567";
        str = str.replaceAll( "[^\\d]", "" );
        assertEquals("8181234567", str);

        str = "818_ 123-4567";
        str = str.replaceAll( "[^\\d]", "" ).substring(0,3);
        assertEquals("818", str);
    }
    
    @Test
    public void testMatches() {
        String line = "abcdef|ghi";
        assertTrue(line.matches("[\\w|]+"));
        line = "abc_def|ghi";
        assertTrue(line.matches("[\\w|]+"));
        line = "abc_def|ghi?";
        assertFalse(line.matches("[\\w|]+"));
        
        // (?s) means dot will match line break character too. (?i) means case_insensitive
        line = "abc_def|ghi?";
        assertTrue(line.matches("ab.*"));
        assertFalse(line.matches("aB.*"));
        assertTrue(line.matches("(?i)aB.*"));
        line = "abc_def|ghi?\njkl";
        assertFalse(line.matches("(?i)aB.*"));
        assertTrue(line.matches("(?s)(?i)aB.*"));
    }
}

