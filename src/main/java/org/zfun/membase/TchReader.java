package org.zfun.membase;

import java.util.HashMap;

import tokyocabinet.HDB;

public class TchReader {
    private HDB tch = null;
    private String tchFilename;
    private Integer readblockSize;

    public TchReader(String tchFilename, Integer readblockSize) {
        this.tchFilename = tchFilename;
        this.readblockSize = readblockSize;
        // open the database
        tch = new HDB();
        if (!tch.open(this.tchFilename, HDB.OREADER | HDB.ONOLCK)) {
            int ecode = tch.ecode();
            System.err.println("Open error: " + HDB.errmsg(ecode));
            System.exit(1);
        }
        tch.iterinit();
        
    }

    public void closeTch() {
        if (tch != null) {
            tch.close();
            tch = null;
        }
    }

    public HashMap<String, Object> readNextBlock() {
        HashMap<String, Object> keyValues = new HashMap<String, Object>();
        byte[] key;
        while ((key = (byte[]) tch.iternext()) != null) {
            final String keyStr = new String(key);
            final byte[] value = tch.get(keyStr.getBytes());
            keyValues.put(keyStr, value);
            if (keyValues.size() == readblockSize)
                break;
        }
        return keyValues;
    }

}
