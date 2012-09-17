package org.zfun;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;

import com.google.gson.Gson;

public class JSONObjectTest {
    private Gson gson = new Gson();

    @Test
    public void testJSONObject() throws JSONException {
        // From GsonTest
        int id = 1;
        String name = "one";
        String version = "version";
        PlaceHolder ph = new PlaceHolder();
        ph.setId(id);
        ph.setName(name);
        ph.setVersion(version);
        String gStr = gson.toJson(ph);
        assertEquals("{\"id\":1,\"name\":\"one\",\"version\":\"version\"}", gStr);

        JSONTokener t = new JSONTokener(gStr);
        JSONObject obj = new JSONObject(t);

        assertTrue(obj.has("id"));
        assertEquals(1, obj.get("id"));
        assertTrue(obj.has("name"));
        assertEquals("one", obj.get("name"));
        assertTrue(obj.has("version"));
        assertEquals("version", obj.get("version"));
    }

    @Test
    public void testJSONObject2() throws JSONException {
        JSONTokener t = new JSONTokener(
                "{\"good\":0,\"holders\":[{\"id\":1,\"name\":\"one\",\"version\":\"v1\"},{\"id\":2,\"name\":\"two\",\"version\":\"v2\"}]}");
        JSONObject obj = new JSONObject(t);

        assertTrue(obj.has("good"));
        assertEquals(0, obj.get("good"));
        JSONArray ar = (JSONArray) obj.get("holders");
        JSONObject jsonObj = (JSONObject) ar.get(0);
        assertEquals(1, jsonObj.get("id"));
        assertEquals("one", jsonObj.get("name"));
        assertEquals("v1", jsonObj.get("version"));
        jsonObj = (JSONObject) ar.get(1);
        assertEquals(2, jsonObj.get("id"));
        assertEquals("two", jsonObj.get("name"));
        assertEquals("v2", jsonObj.get("version"));
    }

    @Test
    public void testValidation() throws JSONException {
        boolean exceptionThrown = false;
        try {
            JSONTokener t = new JSONTokener(
                    "{\"good\":,\"holders\":[{\"id\":1,\"name\":\"one\",\"version\":\"v1\"},{\"id\":2,\"name\":\"two\",\"version\":\"v2\"}]}");
            @SuppressWarnings("unused")
            JSONObject obj = new JSONObject(t);
        } catch (JSONException jsone) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }
}
