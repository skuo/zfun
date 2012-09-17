package org.zfun.generic.gettype;

/**
 * User: skuo
 * Date: Jun 16, 2011
 */
public class GenericUsage {
    private GenericType<?> reference;
    
    public void method(Object arg) {
        _helper(reference, arg);
    }
    
    private static <T> void _helper(GenericType<T> reference, Object arg) {
        reference.method(reference.getType().cast(arg));
    }
}
