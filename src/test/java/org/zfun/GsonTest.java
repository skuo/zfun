package org.zfun;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;

/**
 * User: skuo Date: Apr 5, 2011
 */
public class GsonTest {
    private Gson gson = new Gson();

    @Test
    public void testGson() {
        // Figure out how gson deal with missing fields which are new fields.
        // This simulates the evolution of java Class.
        int id = 1;
        String name = "one";
        String version = "version";

        // Simple, straight forward translation to json
        PlaceHolder ph = new PlaceHolder();
        ph.setId(id);
        ph.setName(name);
        ph.setVersion(version);
        String gStr = gson.toJson(ph);
        assertEquals("{\"id\":1,\"name\":\"one\",\"version\":\"version\"}", gStr);

        // testing translation of an older version
        PlaceHolder reconstitutedPh = gson.fromJson("{\"id\":1,\"name\":\"one\"}", PlaceHolder.class);
        assertEquals(id, reconstitutedPh.getId());
        assertEquals(name, reconstitutedPh.getName());
        assertNull(reconstitutedPh.getVersion());

        // testing translation of a newer version that has an additional field
        // 'extant'
        reconstitutedPh = gson.fromJson("{\"id\":1,\"name\":\"one\",\"extant\":\"none\"}", PlaceHolder.class);
        assertEquals(id, reconstitutedPh.getId());
        assertEquals(name, reconstitutedPh.getName());
        assertNull(reconstitutedPh.getVersion());
    }

    @Test
    public void testHolders() {
        PlaceHolder ph1 = new PlaceHolder();
        ph1.setId(1);
        ph1.setName("one");
        ph1.setVersion("v1");
        PlaceHolder ph2 = new PlaceHolder();
        ph2.setId(2);
        ph2.setName("two");
        ph2.setVersion("v2");

        PlaceHolders holders = new PlaceHolders();
        holders.add(ph1);
        holders.add(ph2);
        String gStr = gson.toJson(holders);
        assertEquals("{\"holders\":[{\"id\":1,\"name\":\"one\",\"version\":\"v1\"},{\"id\":2,\"name\":\"two\",\"version\":\"v2\"}]}",
                gStr);
        
        List<PlaceHolder> phs = new ArrayList<PlaceHolder>();
        phs.add(ph1);
        phs.add(ph2);
        gStr = gson.toJson(phs);
        assertEquals("[{\"id\":1,\"name\":\"one\",\"version\":\"v1\"},{\"id\":2,\"name\":\"two\",\"version\":\"v2\"}]",
                gStr);
    }
}
