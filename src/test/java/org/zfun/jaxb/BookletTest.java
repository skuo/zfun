package org.zfun.jaxb;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

/**
 * User: skuo
 * Date: Jun 6, 2011
 */
public class BookletTest {
    private static final String BOOKLET_XML_FILE = "./booklet-jaxb.xml";

    @Test
    public void testBook() throws JAXBException, IOException {

        // create books
        Booklet booklet1 = new Booklet();
        booklet1.setName("The Gamelet");
        booklet1.setAuthor("Neil Strauss");

        // create JAXB context and instantiate marshaller
        JAXBContext context = JAXBContext.newInstance(Booklet.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(booklet1, System.out);

        Writer w = null;
        try {
            w = new FileWriter(BOOKLET_XML_FILE);
            m.marshal(booklet1, w);
        } finally {
            w.close();
        }
        
        // get variables from the created xml file
        System.out.println("\nOutput form our XML files: ");
        Unmarshaller um = context.createUnmarshaller();
        Booklet booklet = (Booklet) um.unmarshal(new FileReader(BOOKLET_XML_FILE));
        System.out.format("%s from %s\n", booklet.getName(), booklet.getAuthor());
    }
}
