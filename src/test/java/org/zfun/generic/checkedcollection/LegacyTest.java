package org.zfun.generic.checkedcollection;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
 * User: skuo
 * Date: Jun 16, 2011
 */
public class LegacyTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testLegacySuccess() {
        List<String> strings = Collections.checkedList(Legacy.create(),  String.class);
        Legacy.insert(strings, "first");  
        //
        List<Integer> ints = Collections.checkedList(Legacy.create(),  Integer.class);
        Legacy.insert(ints, 1);  
    }

    @SuppressWarnings("unchecked")
    @Test(expected=ClassCastException.class)
    public void testLegacyFailure() {
        List<String> strings = Collections.checkedList(Legacy.create(),  String.class);
        Legacy.insert(strings, new Date());  
    }
}
