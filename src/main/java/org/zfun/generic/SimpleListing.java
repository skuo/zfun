package org.zfun.generic;

import java.io.Serializable;

/**
 * User: skuo
 * Date: Apr 11, 2011
 */
public class SimpleListing implements Serializable, Listing {

    private static final long serialVersionUID = -1072446276423121345L;

    public void addProperty(String key, Object value) {
        // do nothing
        ;
    }

    public Object getProperty(String key)
    {
        return key;
    }
}
