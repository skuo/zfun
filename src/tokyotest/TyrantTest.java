package org.zfun.tokyo;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import tokyotyrant.RDB;
import tokyotyrant.transcoder.ByteArrayTranscoder;
import tokyotyrant.transcoder.IntegerTranscoder;

import com.atti.ypad.clients.profile.ProfileBuffers;
import com.atti.ypad.clients.profile.ProfileBuffers.KeyValue;
import com.atti.ypad.clients.profile.ProfileBuffers.Profile;
import com.atti.ypad.clients.profile.ProfileBuffers.ProfilePayload;
import com.atti.ypad.clients.common.ProfileIdBuffers;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * User: skuo
 * Date: Apr 21, 2011
 */
public class TyrantTest {

    //@Test 
    public void testGraTdbDev() {
        Object key;
        Object value;
        
        RDB rdb = new RDB();
        rdb.open(new InetSocketAddress("gratdb-dev.np.wc1.yellowpages.com", 4044));
        
        // put operations
        if (!rdb.put("foo", "hop") || !rdb.put("bar", "step") || !rdb.put("baz", "jump")) {
            System.err.println("put error");
        }
        
        // put and add int
        IntegerTranscoder intTranscoder = new IntegerTranscoder();
        rdb.put("totalCount", 3, intTranscoder);
        System.out.format("totalCount=%d\n", (Integer) rdb.get("totalCount", intTranscoder));
        
        rdb.put("genderCount", 0, intTranscoder);  // (re)set to 0
        int genderCount = rdb.addint("genderCount", 3);
        genderCount = rdb.addint("genderCount", 4);
        System.out.format("genderCount=%d\n", genderCount);
        
        // put and get for protobuf
        List<ProfileBuffers.KeyValue> keyValues = new ArrayList<ProfileBuffers.KeyValue>();
        ProfileBuffers.KeyValue kv0 = ProfileBuffers.KeyValue.newBuilder().setKeyId(0).setValue("0").build();
        keyValues.add(kv0);
        ProfileBuffers.KeyValue kv1 = ProfileBuffers.KeyValue.newBuilder().setKeyId(1).setValue("1").build();
        keyValues.add(kv1);
        ProfileBuffers.KeyValue kv2 = ProfileBuffers.KeyValue.newBuilder().setKeyId(2).setValue("2").build();
        keyValues.add(kv2);
        ProfileBuffers.ProfilePayload payload = ProfileBuffers.ProfilePayload.newBuilder().addAllPayload(keyValues).build();
        ByteArrayTranscoder byteArrayTranscoder = new ByteArrayTranscoder();
        rdb.put("payload", payload.toByteArray(), byteArrayTranscoder);
        byte[] data = (byte[]) rdb.get("payload", byteArrayTranscoder);
        try {
            ProfilePayload payload2 = ProfilePayload.parseFrom(data);
            for (ProfileBuffers.KeyValue kv : payload2.getPayloadList())
                System.out.format("keyId=%d, value=%s\n", kv.getKeyId(), kv.getValue());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        // iterate and print out payload
        rdb.iterinit();
        while ((key = (String) rdb.iternext()) != null) {
            System.out.print("key=" + key + ": ");
            value = rdb.get(key, byteArrayTranscoder);
            data = (byte[]) value;
            try {
                ProfilePayload payload2 = ProfilePayload.parseFrom(data);
                for (ProfileBuffers.KeyValue kv : payload2.getPayloadList())
                    System.out.format("keyId=%d, value=%s\n", kv.getKeyId(), kv.getValue());
            } catch (InvalidProtocolBufferException e) {
                //e.printStackTrace();
                System.out.println("error");
            }
        }

        // close the connection
        rdb.close();

    }
    
    //@Test
    public void testTdb() {
        Object key;
        Object value;
        
        RDB rdb = new RDB();
        rdb.open(new InetSocketAddress("tdb-db.np.wc1.yellowpages.com", 4040));
        
        ByteArrayTranscoder byteArrayTranscoder = new ByteArrayTranscoder();
        // iterate and print out profile
        int i = 0;
        rdb.iterinit();
        while ((key = (String) rdb.iternext()) != null) {
            i++;
            if ((i % 1000) == 0)
                System.out.println(i);
            //System.out.print("key=" + key + ": ");
            value = rdb.get(key, byteArrayTranscoder);
            byte[] data = (byte[]) value;
            try {
                Profile profile = Profile.parseFrom(data);
                if (profile.getSearchesList() != null && profile.getSearchesList().size() > 0) {
                    //if (profile.getSearchesList().size() < 2)
                    //    continue;
                    for (ProfileBuffers.Search search : profile.getSearchesList())
                        System.out.format("(%d) term=%s, location=%s, weight=%g\n", i, search.getTerm(),
                                search.getLocation(), search.getWeight());
                    System.out.format("(%d) optOutFlag=%b\n", i, profile.getOptOutFlag());
                    for (ProfileBuffers.Impression im : profile.getImpressionsList())
                        System.out.format("(%d) ypid=%s, timestamp=%d\n", i, im.getYpId(), im.getTimestamp());
                    System.out.println();
                }
            } catch (InvalidProtocolBufferException ipbe) {
                //e.printStackTrace();
                //System.out.println("error");
            } catch (Exception e) {
                ;
            }
        }

        System.out.format("Total records=%d\n", i);
        // close the connection
        rdb.close();
        
    }

    //@Test
    public void testCookieTdb() {
        String key;
        Object value;
        
        RDB rdb = new RDB();
        rdb.open(new InetSocketAddress("tdb-db2.np.wc1.yellowpages.com", 4040));
        
        ByteArrayTranscoder byteArrayTranscoder = new ByteArrayTranscoder();
        // iterate and print out profile
        int i = 0;
        rdb.iterinit();
        while ((key = (String) rdb.iternext()) != null) {
            i++;
            if ((i % 1000) == 0)
                System.out.println(i);
            //System.out.print("key=" + key + ": ");
            value = rdb.get(key, byteArrayTranscoder);
            byte[] data = (byte[]) value;
            if (data == null)
                continue;
            try {
                if (key.contains("pidvid")) {
                    ProfileIdBuffers.ProfileId profileId = ProfileIdBuffers.ProfileId.parseFrom(data);
                    System.out.format("(%d) tuid=%s, timestamp=%d\n\n", i, profileId.getId(), profileId.getTimestamp());
                } else if (key.contains("cookieprofile")) {
                    Profile profile = Profile.parseFrom(data);
                    if (profile.getSearchesList() != null && profile.getSearchesList().size() > 0) {
                        for (ProfileBuffers.Search search : profile.getSearchesList())
                            System.out.format("(%d) term=%s, location=%s, weight=%g\n", i, search.getTerm(),
                                    search.getLocation(), search.getWeight());
                        System.out.format("(%d) optOutFlag=%b\n", i, profile.getOptOutFlag());
                        for (ProfileBuffers.Impression im : profile.getImpressionsList())
                            System.out.format("(%d) ypid=%s, timestamp=%d\n", i, im.getYpId(), im.getTimestamp());
                        System.out.println();
                    }
                }
            } catch (InvalidProtocolBufferException ipbe) {
                //e.printStackTrace();
                //System.out.println("error");
            } catch (Exception e) {
                ;
            }
        }

        System.out.format("Total records=%d\n", i);
        // close the connection
        rdb.close();
        
    }

    //@Test
    public void testKiwiTdb() {
        String key;
        Object value;
        
        RDB rdb = new RDB();
        rdb.open(new InetSocketAddress("tdb-db.np.wc1.yellowpages.com", 4041));
        
        ByteArrayTranscoder byteArrayTranscoder = new ByteArrayTranscoder();
        // iterate and print out profile
        int i = 0;
        rdb.iterinit();
        while ((key = (String) rdb.iternext()) != null) {
            i++;
            if ((i % 1000) == 0)
                System.out.println(i);
            //System.out.print("key=" + key + ": ");
            value = rdb.get(key, byteArrayTranscoder);
            byte[] data = (byte[]) value;
            if (data == null)
                continue;
            try {
                if (key.contains("profile")) {
                    ProfilePayload profilePayload = ProfilePayload.parseFrom(data);
                    if (profilePayload.getPayloadList() != null && profilePayload.getPayloadCount() > 0) {
                        for (ProfileBuffers.KeyValue kv : profilePayload.getPayloadList())
                            System.out.format("(%d) keyId=%d, value=%s\n", i, kv.getKeyId(), kv.getValue());
                        System.out.println();
                    }
                }
            } catch (InvalidProtocolBufferException ipbe) {
                //e.printStackTrace();
                //System.out.println("error");
            } catch (Exception e) {
                ;
            }
        }

        System.out.format("Total records=%d\n", i);
        // close the connection
        rdb.close();
        
    }

    @Test 
    public void testWriteKiwiTdb() {
        RDB rdb = new RDB();
        //rdb.open(new InetSocketAddress("tdb-db.np.wc1.yellowpages.com", 4041));
        rdb.open(new InetSocketAddress("gratdb-dev.np.wc1.yellowpages.com", 4041));
                
        // put and get for protobuf.Payload
        ByteArrayTranscoder byteArrayTranscoder = new ByteArrayTranscoder();
        ProfilePayload payload1 = ProfilePayload.newBuilder()
                .addPayload(KeyValue.newBuilder().setKeyId(10).setValue("male").build())
                .addPayload(KeyValue.newBuilder().setKeyId(11).setValue("whatever").build())
                .addPayload(KeyValue.newBuilder().setKeyId(12).setValue("whenever").build())
                .build();
        rdb.put("dev::profile::skuoSub5", payload1.toByteArray(), byteArrayTranscoder);
        ProfilePayload payload2 = ProfilePayload.newBuilder()
                .addPayload(KeyValue.newBuilder().setKeyId(10).setValue("male").build())
                .addPayload(KeyValue.newBuilder().setKeyId(11).setValue("whatever").build())
                .addPayload(KeyValue.newBuilder().setKeyId(12).setValue("whenever").build()).build();
        rdb.put("dev::profile::skuoSub", payload2.toByteArray(), byteArrayTranscoder);
        
        String[] keys = {"dev::profile::skuoSub5", "dev::profile::skuoSub"};
        for (String key : keys) {
            byte[] data = (byte[]) rdb.get(key, byteArrayTranscoder);
            try {
                ProfilePayload payload = ProfilePayload.parseFrom(data);
                for (ProfileBuffers.KeyValue kv : payload.getPayloadList())
                    System.out.format("keyId=%d, value=%s\n", kv.getKeyId(), kv.getValue());
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }

        // close the connection
        rdb.close();

    }
    
}
