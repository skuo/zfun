package org.zfun.seven;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

public class CoinTest {

    @Test
    public void testBinaryVariable() {
        int i = 0b10001;
        assertEquals(17, i);
    }

    @Test
    public void test_InNumber() {
        int i = 1_23;
        assertEquals(123, i);
    }

    @Test
    public void testStringInSwitch() {
        Coin c = new Coin();
        assertEquals(0, c.DayToInt("SunDay"));
    }

    @Test
    public void testTryWithResource() throws IOException {
        // SE 6
        String str = null;
        BufferedReader br = new BufferedReader(new FileReader("book-jaxb.xml"));
        try {
            str = br.readLine();
        } finally {
            if (br != null)
                br.close();
        }
        assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", str);
        boolean exceptionCaught = false;
        try {
            br.readLine();
        } catch (Exception e) {
            // reading from a closed BufferedReader should throw an exception
            exceptionCaught = true;
        }
        assertTrue(exceptionCaught);

        // SE 7
        // Exception in try block is thrown and exception in try-with-resources block is suppressed.
        exceptionCaught = false;
        try (BufferedReader br1 = new BufferedReader(new FileReader("book-jaxb.xml"))) {
            String s = br1.readLine();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", s);
        } catch (IOException ioe) {
            exceptionCaught = true;
        } finally {
            System.out.println("finally is still allowed.  At this point br1 is closed");
        }
        assertFalse(exceptionCaught);

        exceptionCaught = false;
        try (BufferedReader br1 = new BufferedReader(new FileReader("junk"))) {
            String s = br1.readLine();
            assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>", s);
        } catch (IOException ioe) {
            exceptionCaught = true;
        }
        assertTrue(exceptionCaught);
    }
    
    @Test
    public void testCatchMultipleException() {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("junk.xml"));
            br.readLine();
            throwSQLException();
        } catch (IOException | SQLException e) {
            // e is implicit final
            assertTrue(e instanceof FileNotFoundException);
        }
        
        try {
            br = new BufferedReader(new FileReader("book-jaxb.xml"));
            br.readLine();
            throwSQLException();
        } catch (IOException | SQLException e) {
            // e is implicit final
            assertTrue(e instanceof SQLException);
        }
    }
    
    private void throwSQLException() throws SQLException {
        throw new SQLException();
    }
}
