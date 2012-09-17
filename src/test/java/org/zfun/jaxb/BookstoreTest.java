package org.zfun.jaxb;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;

/**
 * User: skuo Date: Apr 11, 2011
 */
public class BookstoreTest {
    private static final String BOOKSTORE_XML_FILE = "./bookstore-jaxb.xml";

    @Test
    public void testBookstore() throws JAXBException, IOException {
        List<Book> bookList = new ArrayList<Book>();

        // create books
        Book book1 = new Book();
        book1.setIsbn("978-0060554736");
        book1.setName("The Game");
        book1.setAuthor("Neil Strauss");
        book1.setPublisher("Harpercollins");
        bookList.add(book1);

        Book book2 = new Book();
        book2.setIsbn("978-3832180577");
        book2.setName("Feuchtegebiete");
        book2.setAuthor("Charlotte Roche");
        book2.setPublisher("Dumont Buchverlag");
        bookList.add(book2);

        // Create bookstore, assigning book
        Bookstore bookstore = new Bookstore();
        bookstore.setName("Fraport Bookstore");
        bookstore.setLocation("Frankfurt Airport");
        bookstore.setBooks(bookList);

        // create JAXB context and instantiate marshaller
        JAXBContext context = JAXBContext.newInstance(Bookstore.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        m.marshal(bookstore, System.out);

        Writer w = null;
        try {
            w = new FileWriter(BOOKSTORE_XML_FILE);
            m.marshal(bookstore, w);
        } finally {
            w.close();
        }
        
        // get variables from the created xml file
        System.out.println("\nOutput form our XML files: ");
        Unmarshaller um = context.createUnmarshaller();
        Bookstore bs = (Bookstore) um.unmarshal(new FileReader(BOOKSTORE_XML_FILE));
        for (Book book : bs.getBookList()) {
            System.out.format("%s from %s published by %s\n", book.getName(), book.getAuthor(), book.getPublisher());
        }
    }
}
