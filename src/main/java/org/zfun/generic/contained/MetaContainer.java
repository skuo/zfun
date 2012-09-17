package org.zfun.generic.contained;

import java.util.List;

/**
 * User: skuo Date: Jun 16, 2011
 */
public class MetaContainer {
    private Container<? extends Contained> container;

    public void setContainer(Container<? extends Contained> container) {
        this.container = container;
    }
    
    public void add(Contained elem) {
        _add(container, elem);
    }
    
    /*
     * Making the type a concrete parameterization for an unknown type for the compiler
     */
    private static <T extends Contained> void _add(Container<T> container, Contained elem) {
        container.add(container.getElementType().cast(elem));
    }
    
    public List<? extends Contained> elements() {
        return container.elements();
    }
    
}
