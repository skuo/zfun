package org.zfun;

public class GenericCast {

    @SuppressWarnings("unchecked")
    static <E> E cast(Object o) {
        return (E) o;
    }
}
