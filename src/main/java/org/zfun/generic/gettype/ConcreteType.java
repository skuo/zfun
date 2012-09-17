package org.zfun.generic.gettype;

/**
 * User: skuo
 * Date: Jun 16, 2011
 */
public class ConcreteType implements GenericType<String>{

    public void method(String arg) {
    }

    public Class<String> getType() {
        return String.class;
    }

}
