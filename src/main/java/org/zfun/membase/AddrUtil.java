package org.zfun.membase;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class AddrUtil {

    public static List<URI> getAddresses(String commaSeperatedURIs) {
        if (StringUtils.isEmpty(commaSeperatedURIs)) throw new IllegalArgumentException("must be valid URI");
        final String[] uris = commaSeperatedURIs.split(",");
        List<URI> uriList = new ArrayList<URI>();
        for (String uri : uris) {
            addURI(uriList, uri);
        }
        return uriList;
    }

    private static void addURI(List<URI> uriList, String uri) {
        try {
            uriList.add(new URI(uri));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("not a valid URI " + uri);
        }
    }
}
