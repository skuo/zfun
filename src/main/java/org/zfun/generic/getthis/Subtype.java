package org.zfun.generic.getthis;

/**
 * User: skuo
 * Date: Jun 16, 2011
 */
public class Subtype extends SelfReferrentialType<Subtype>{
    @Override
    protected Subtype getThis() {
        return this;
    }
}
