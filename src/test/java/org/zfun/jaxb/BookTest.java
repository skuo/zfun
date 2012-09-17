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
 * Date: Apr 11, 2011
 */
public class BookTest {
    private static final String BOOK_XML_FILE = "./book-jaxb.xml";

    @Test
    public void testBook() throws JAXBException, IOException {

        // create books
        Book book1 = new Book();
        book1.setIsbn("978-0060554736");
        book1.setName("The Game");
        book1.setAuthor("Neil Strauss");
        book1.setPublisher("Harpercollins");

        // create JAXB context and instantiate marshaller
        JAXBContext context = JAXBContext.newInstance(Book.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(book1, System.out);

        Writer w = null;
        try {
            w = new FileWriter(BOOK_XML_FILE);
            m.marshal(book1, w);
        } finally {
            w.close();
        }
        
        // get variables from the created xml file
        System.out.println("\nOutput form our XML files: ");
        Unmarshaller um = context.createUnmarshaller();
        Book book = (Book) um.unmarshal(new FileReader(BOOK_XML_FILE));
        System.out.format("%s from %s published by %s\n", book.getName(), book.getAuthor(), book.getPublisher());
    }
}
