package org.zfun.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: skuo Date: Apr 11, 2011
 */
@XmlRootElement(namespace = "org.zfun.jaxb")
public class Bookstore {
    // XmlElementWrapper generates a wrapper element around XML representation
    @XmlElementWrapper(name = "books")
    // XmlElement override the name defined in Book.
    @XmlElement(name = "book")
    private List<Book> bookList;
    private String name;
    private String location;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBooks(List<Book> bookList) {
        this.bookList = bookList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
