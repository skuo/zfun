package org.zfun.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;

/**
 * A simple class to how to specify charset so that ñ and ä can be read correctly.  On Mac, take Charset.defaultCharset()
 * to make this work.  JavaCsv defaults to ISO.
 * @author skuo
 *
 */
public class CsvUtil {

    public static List<String[]> read(String filename, Charset charset) throws IOException {
        List<String[]> l = new ArrayList<String[]>();
        //CsvReader reader = new CsvReader(new InputStreamReader(new FileInputStream(filename),charset));
        CsvReader reader = new CsvReader(new InputStreamReader(new FileInputStream(filename),charset));
        // read the data lines
        while (reader.readRecord()) {
            // getValues() trim the leading and ending space.
            String[] values = reader.getValues();
            l.add(values);
            /*
            System.out.println("[read] " + Arrays.toString(values));
            String value3 = reader.get(3).trim();
            String utf3 = UtfUtil.StrInUtfRepresentation(value3);
            System.out.format("[read] %s %s%n", value3, utf3);
            */
        }
        return l;
    }

    public static List<String[]> read(String filename, char delimiter, Charset charset) throws IOException {
        List<String[]> l = new ArrayList<String[]>();
        CsvReader reader = new CsvReader(filename, delimiter,charset);
        // read the data lines
        while (reader.readRecord()) {
            // getValues() trim the leading and ending space.
            String[] values = reader.getValues();
            l.add(values);
        }
        return l;
    }

    /**
     * Use FileReader that defaults to Charset.defaultCharset and BufferedReader to read ñ and ä correctly. 
     * @param filename
     * @throws IOException
     */
    public static List<String[]> readAndParse(String filename) throws IOException {
        List<String[]> l = new ArrayList<String[]>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                line.trim();
                String[] tokens = line.split(",");
                l.add(tokens);
                /*
                System.out.println("[readAndParse] " + Arrays.toString(tokens));
                String value3 = tokens[3];
                String utf3 = UtfUtil.StrInUtfRepresentation(value3);
                System.out.format("[readAndParse] %s %s%n", value3, utf3);
                */
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            br.close();
        }
        return l;
    }
    
    public static void main(String[] args) throws IOException {
        System.out.println(Charset.defaultCharset());
        CsvUtil.read("src/main/data/test.csv", Charset.defaultCharset());
        CsvUtil.readAndParse("src/main/data/test.csv");
    }
}
