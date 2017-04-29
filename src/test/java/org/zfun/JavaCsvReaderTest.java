package org.zfun;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.csvreader.CsvReader;

public class JavaCsvReaderTest {
    private static String LTR_FILENAME = "src/test/resources/org/zfun/ltr.csv";

    @Test
    public void validateStringSplit() throws IOException {
        List<String> csvData = readLtrFileWithCsvReader(LTR_FILENAME);
        assertEquals(7, csvData.size());
        List<String> splitData = readLtrFileWithStringSplit(LTR_FILENAME);
        assertEquals(7, splitData.size());
        for (int i = 0; i < csvData.size(); i++)
            assertEquals(csvData.get(i), splitData.get(i));
    }

    private List<String> readLtrFileWithCsvReader(String filename) throws FileNotFoundException, IOException {
        List<String> data = new ArrayList<String>();
        try {
            CsvReader reader = new CsvReader(filename);
            int lineRead = 0;
            while (reader.readRecord()) {
                if (reader.getColumnCount() != 5)
                    continue;
                lineRead++;
                String partner = reader.get(0);
                Integer impressions = null;
                if (null != reader.get(1) && !"".equals(reader.get(1)))
                    impressions = Integer.valueOf(reader.get(1));
                Integer clicks = null;
                if (null != reader.get(2) && !"".equals(reader.get(2)))
                    clicks = Integer.valueOf(reader.get(2));
                String customer = reader.get(3);
                String ypid = reader.get(4);
                data.add(String.format("[%d] partner=%s, impressions=%d, clicks=%d, customer=%s, ypid=%s,\n", lineRead,
                        partner, impressions, clicks, customer, ypid));

            }

            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
            data.add(String.format("[%d] partner=%s, impressions=%d, clicks=%d, customer=%s, ypid=%s,\n", lineRead,
                    partner, impressions, clicks, customer, ypid));
        }
        if (reader != null)
            reader.close();
        return data;
    }

}
