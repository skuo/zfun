package org.zfun.tokyo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tokyocabinet.BDB;

import com.atti.tdb.common.util.TokyoCabinet;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * User: skuo
 * Date: Apr 25, 2011
 */
public class CabinetTest {

    @Test
    public void test() throws InvalidProtocolBufferException {
        TokyoCabinet tc = new TokyoCabinet();
        tc.setTokyoCabinet(new BDB());
        tc.setTch_file("/home/t/ypad/tdb_db/kiwi-headers.tcb");
        byte[] data = tc.get("dev::profile::10");
        assertEquals("gender_cd", new String(data));
    }
}
