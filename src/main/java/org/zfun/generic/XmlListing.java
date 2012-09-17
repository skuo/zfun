package org.zfun.generic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * User: skuo 
 * Date: Apr 11, 2011
 */
public class XmlListing implements Serializable, Listing {
    private Map<String, Object> properties = new HashMap<String, Object>();

    private static final long serialVersionUID = -1879090320918149989L;

    public void addProperty(String key, Object value) {
        properties.put(key, value);
    }

    public Object getProperty(String key)
    {
        return key;
    }
}
