package org.zfun.generic;

import org.junit.Test;
import org.zfun.PlaceHolder;
import org.zfun.SecondPlaceHolder;

/**
 * User: skuo Date: Jun 10, 2011
 */
public class TyrantTest {

    @Test
    public void testConstructor() throws InstantiationException, IllegalAccessException {
        Tyrant<PlaceHolder> tPlaceHolder = new Tyrant<PlaceHolder>(PlaceHolder.class);
        System.out.println(tPlaceHolder.getClass() + " " + tPlaceHolder.getParameterizedType());
        System.out.println("parse=" + tPlaceHolder.parse("string"));

        Tyrant<SecondPlaceHolder> tPlaceHolder2 = new Tyrant<SecondPlaceHolder>(SecondPlaceHolder.class);
        System.out.println(tPlaceHolder2.getClass() + " " + tPlaceHolder2.getParameterizedType());
        System.out.println("parse=" + tPlaceHolder2.parse("string"));
    }
}
