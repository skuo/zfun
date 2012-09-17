package org.zfun.generic;

/**
 * User: skuo Date: Apr 11, 2011
 */
public interface Response<T> {

    public void addCommonProperty(String key, Object value);

    public T createAndAddNewListing();

}
