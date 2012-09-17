package org.zfun;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

import org.junit.Test;

/**
 * User: skuo
 * Date: May 14, 2011
 */
public class URLTest {
    
    @Test
    public void testFileURL() throws IOException {
        Path p = Paths.get("./book-jaxb.copy");
        // read plain text file
        URL fileUrl = p.toUri().toURL();
        URLConnection fileConnection = fileUrl.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(fileConnection.getInputStream()));
        String line = null;
        int numLines = 0;
        while ( (line = br.readLine()) != null) {
            System.out.println(line);
            numLines++;
        }
        System.out.println(" ");
        
        // read .gz file
        p = Paths.get("./book-jaxb.gz");
        fileUrl = p.toUri().toURL();
        fileConnection = fileUrl.openConnection();
        br = new BufferedReader(new InputStreamReader(new GZIPInputStream(fileConnection.getInputStream())));
        line = null;
        int numGzLines = 0;
        while ( (line = br.readLine()) != null) {
            System.out.println(line);
            numGzLines++;
        }
        assertEquals(numGzLines, numLines);
        System.out.println(" ");
    }
}
