package org.zfun.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.junit.Test;

public class CsvUtilTest {

    @Test
    public void testRead() throws IOException {
        // read file encode in utf-8
        List<String[]>l = CsvUtil.read("src/test/data/test.utf8.csv", ',', Charset.forName("UTF-8"));
        validate(l);

        l = CsvUtil.read("src/test/data/test.utf8.csv", ',', Charset.forName("UTF-8"));
        validate(l);
        
        CsvUtil.readAndParse("src/test/data/test.utf8.csv");
        validate(l);
    }

    private void validate(List<String[]> l) {
        assertEquals("Patricio Maulen Nuñez Group 1", l.get(0)[1]);
        assertEquals("NeluaMNelu", l.get(0)[2]);
        assertEquals("Nuñez", l.get(0)[3]);
        assertEquals("Mikko Mäkelä Group 1", l.get(1)[1]);
        assertEquals("pokey4ever", l.get(1)[2]);
        assertEquals("Mäkelä", l.get(1)[3]);
    }
}
