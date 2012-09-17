package org.zfun.generic;

import java.io.Serializable;

/**
 * User: skuo
 * Date: Apr 11, 2011
 */
public class XmlResponse implements Serializable, Response<XmlListing>{
    private static final long serialVersionUID = -2947004488449208800L;

    public void addCommonProperty(String key, Object value) {
    }

    public XmlListing createAndAddNewListing() {
        return new XmlListing();
    }

}
