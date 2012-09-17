package org.zfun.generic.contained;

import java.util.List;

/**
 * User: skuo Date: Jun 16, 2011
 */
public interface Container<T extends Contained> {
    void add(T element);

    List<T> elements();
    
    Class<T> getElementType();
}
