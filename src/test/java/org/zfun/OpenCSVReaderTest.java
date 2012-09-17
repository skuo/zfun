package org.zfun;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import au.com.bytecode.opencsv.CSVReader;

public class OpenCSVReaderTest {

    private static String LTR_FILENAME = "src/test/resources/org/zfun/ltr.csv";
    //private static String BAD_LTR_FILENAME = "/home/skuo/data/log.astroman.ltr.wc1/ltr-20120118-nd-2012-01-20_03-11-31.csv";
    private static String BAD_LTR_FILENAME = "/home/skuo/data/log.astroman.ltr.wc1/ltr-20120206-nd.csv";

    @Ignore
    @Test
    public void findBadCSVLine() throws FileNotFoundException, IOException {
        CSVReader reader = new CSVReader(new FileReader(BAD_LTR_FILENAME));
        int lineRead = 0;
        String[] values;
        while ((values = reader.readNext()) != null) {
            if (values.length != 5)
                continue;
            lineRead++;
            String partner = values[0];
            Integer impressions = null;
            if (null != values[1] && !"".equals(values[1]))
                impressions = Integer.valueOf(values[1]);
            Integer clicks = null;
            if (null != values[2] && !"".equals(values[2]))
                clicks = Integer.valueOf(values[2]);
            String customer = values[3];
            String ypid = values[4];
            System.out.format("[%d] partner=%s, impressions=%d, clicks=%d, customer=%s, ypid=%s,\n", lineRead, partner, impressions,
                    clicks, customer, ypid);
        }
        System.out.println("End of File");
    }

    @Test
    public void validateStringSplit() throws IOException {
        List<String> csvData = readLtrFileWithCSVReader(LTR_FILENAME);
        assertEquals(5, csvData.size());
        List<String> splitData = readLtrFileWithStringSplit(LTR_FILENAME);
        assertEquals(7, splitData.size());
        for (int i=0; i< csvData.size(); i++)
            assertEquals(csvData.get(i), splitData.get(i));
    }

    private List<String> readLtrFileWithCSVReader(String filename) throws FileNotFoundException, IOException {
        List<String> data = new ArrayList<String>();
        CSVReader reader = new CSVReader(new FileReader(filename));
        int lineRead = 0;
        String[] values;
        while ((values = reader.readNext()) != null) {
            if (values.length != 5)
                continue;
            lineRead++;
            String partner = values[0];
            Integer impressions = null;
            if (null != values[1] && !"".equals(values[1]))
                impressions = Integer.valueOf(values[1]);
            Integer clicks = null;
            if (null != values[2] && !"".equals(values[2]))
                clicks = Integer.valueOf(values[2]);
            String customer = values[3];
            String ypid = values[4];
            data.add(String.format("[%d] partner=%s, impressions=%d, clicks=%d, customer=%s, ypid=%s,\n", lineRead, partner, impressions,
                    clicks, customer, ypid));
        }
        return data;
    }

    private List<String> readLtrFileWithStringSplit(String filename) throws FileNotFoundException, IOException {
        List<String> data = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        int lineRead = 0;
        String[] values;
        String line = null;
        while ((line = reader.readLine()) != null) {
            values = line.split(",", 20);
            if (values.length != 5)
                continue;
            lineRead++;
            String partner = values[0];
            Integer impressions = null;
            if (null != values[1] && !"".equals(values[1]))
                impressions = Integer.valueOf(values[1]);
            Integer clicks = null;
            if (null != values[2] && !"".equals(values[2]))
                clicks = Integer.valueOf(values[2]);
            String customer = values[3];
            String ypid = values[4];
            data.add(String.format("[%d] partner=%s, impressions=%d, clicks=%d, customer=%s, ypid=%s,\n", lineRead, partner, impressions,
                    clicks, customer, ypid));
        }
        return data;
    }
}
