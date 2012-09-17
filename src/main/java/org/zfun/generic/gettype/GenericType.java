package org.zfun.generic.gettype;

/**
 * User: skuo
 * Date: Jun 16, 2011
 */
public interface GenericType<T> {
    public void method(T arg);
    
    Class<T> getType();
}
