package org.zfun.generic.contained;

import java.util.ArrayList;
import java.util.List;

/**
 * User: skuo Date: Jun 16, 2011
 */
public class MyContainer implements Container<MyContained>{
    private final List<MyContained> elems = new ArrayList<MyContained>();

    public void add(MyContained elem) {
        elems.add(elem);
    }

    public List<MyContained> elements() {
        return elems;
    }

    public Class<MyContained> getElementType() {
        return MyContained.class;
    }
}
