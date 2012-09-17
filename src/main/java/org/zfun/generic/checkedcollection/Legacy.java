package org.zfun.generic.checkedcollection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: skuo
 * Date: Jun 16, 2011
 */
public class Legacy {
    public static List create() {
        List rawList = new ArrayList();
        rawList.add("abc");
        return rawList;
    }
    
    // Mixing String and Date.  Ok in raw list but bad in parameterized list
    public static void insert (List rawList, Object o) {
        rawList.add(o); 
    }
}
