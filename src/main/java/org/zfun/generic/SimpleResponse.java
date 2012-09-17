package org.zfun.generic;

import java.io.Serializable;

/**
 * User: skuo
 * Date: Apr 11, 2011
 */
public class SimpleResponse implements Serializable, Response<SimpleListing>{

    private static final long serialVersionUID = 1898976657533697869L;

    public void addCommonProperty(String key, Object value) {
    }

    public SimpleListing createAndAddNewListing() {
        return new SimpleListing();
    }

}
