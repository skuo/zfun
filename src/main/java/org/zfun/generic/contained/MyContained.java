package org.zfun.generic.contained;

/**
 * User: skuo Date: Jun 16, 2011
 */
public class MyContained implements Contained {
    private final String name;

    public MyContained(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
