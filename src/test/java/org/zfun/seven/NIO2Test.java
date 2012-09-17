package org.zfun.seven;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class NIO2Test {

    @Test
    public void testPaths() throws IOException {
        Path p = Paths.get("/tmp/./foo");
        assertEquals("foo", p.getFileName().toString());
        assertEquals("/tmp/.", p.getParent().toString());
        assertEquals("/", p.getRoot().toString());
        
        // remove redundancies from a path
        p = p.normalize();
        assertEquals("/tmp/foo", p.toString());
        assertEquals("/tmp", p.getParent().toString());
        
        // join two paths where the input path is a relative path.  Does not work for absolute input path.
        Path p1 = p.resolve("bar");
        assertEquals("/tmp/foo/bar", p1.toString());
        
        // generate relative path
        Path p_to_p1 = p.relativize(p1);
        assertEquals("bar", p_to_p1.toString());
        Path p1_to_p = p1.relativize(p);
        assertEquals("..", p1_to_p.toString());
        
        // iterate
        StringBuffer sb = new StringBuffer();
        for (Path name : p) {
            sb.append("_");
            sb.append(name);
        }
        assertEquals("_tmp_foo", sb.toString());
        
        // copy file
        Path book = Paths.get("book-jaxb.xml");
        Path tmpBook = Paths.get("/tmp/book-jaxb.xml");
        Files.copy(book,tmpBook, REPLACE_EXISTING);
        assertTrue(Files.exists(tmpBook));
        
        // delete file
        Files.delete(tmpBook);
        assertFalse(Files.exists(tmpBook));
        
        // write to a file
        Charset charset = Charset.forName("US-ASCII");
        String s = "NIO beats old IO.";
        try (BufferedWriter writer = Files.newBufferedWriter(tmpBook, charset)) {
            writer.write(s);
        }
        
        // create a junk symlink
        Path tmpJunk = Paths.get("/tmp/junk.xml");
        Files.createSymbolicLink(tmpJunk, tmpBook);
        assertTrue(Files.exists(tmpJunk));
        Files.delete(tmpJunk);
    }
}
