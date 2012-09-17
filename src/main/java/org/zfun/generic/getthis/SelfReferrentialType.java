package org.zfun.generic.getthis;

/**
 * User: skuo
 * Date: Jun 16, 2011
 */
public abstract class SelfReferrentialType<T extends SelfReferrentialType<T>>{
    private SomeOtherType<T> ref;
    
    protected abstract T getThis();
    
    public void aMethod() {
        ref.m(getThis());
    }
}
