package org.zfun;

import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Test;

/**
 * User: skuo
 * Date: Mar 16, 2012
 */
public class CopyOnWriteArrayListTest {

    /*
     * CopyOnWriteArrayList instantiates an Object[].  All read methods, call getArray() to
     * return a point to this array.  All write method utilizes a ReentrantLock to ensure
     * Object[] is changed by one writer only.
     */
    @Test
    public void testCopyOnWriteArrayList() {
       CopyOnWriteArrayList<String> l = new CopyOnWriteArrayList<String>();
       l.add("one");
       l.get(0);
    }
}
