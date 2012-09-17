package org.zfun.generic;

import org.junit.Test;

/**
 * User: skuo
 * Date: Apr 11, 2011
 */
public class ListingTest {
    @Test
    public void testListing() {
        SimpleListing sListing = new SimpleListing();
        getProperty(sListing, "key");
        
        XmlListing xmlListing = new XmlListing();
        getProperty(xmlListing, "key");
    }
    
    private Object getProperty(Listing listing, String key) {
        return listing.getProperty(key);
    }
}
