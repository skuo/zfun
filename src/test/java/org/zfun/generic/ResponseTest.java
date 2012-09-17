package org.zfun.generic;

import org.junit.Test;

/**
 * User: skuo Date: Apr 11, 2011
 */
public class ResponseTest {

    @Test
    public void testResponse() {
        XmlResponse xmlRes = new XmlResponse();
        add(xmlRes);
        addSubtype(xmlRes);
        addXmlListing(xmlRes);

        // no problem here of assigning XmlListing to Listing
        Listing l = xmlRes.createAndAddNewListing();
        l.addProperty("key", null);

        SimpleResponse simpleRes = new SimpleResponse();
        add(simpleRes);
        addSubtype(simpleRes);
        addSimpleListing(simpleRes);
    }

    // addSubType(Response resData) and addSubtype(Response<? extends Listing>)
    // has the same erasure type
    // private void addSubtype(Response resData) {
    /*
     * Raw type does work but it bypasses generic compile time checking.
     */
    private void add(@SuppressWarnings("rawtypes") Response resData) {

    }

    /*
     * Bounded subtype works
     */
    private void addSubtype(Response<? extends Listing> resData) {
        // simulate AbstractXmlapiFormatter.addPpcListing(...)
        Listing l = resData.createAndAddNewListing();
        l.addProperty("key", null);
    }

    /*
     * This definitely works as the generic type is specified explicitly
     */
    private void addXmlListing(Response<XmlListing> resData) {

    }

    /*
     * This definitely works as the generic type is specified explicitly
     */
    private void addSimpleListing(Response<SimpleListing> resData) {

    }
}
