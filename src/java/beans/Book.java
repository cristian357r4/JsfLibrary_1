/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import db.Database;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IT10
 */
public class Book extends Item {
    private byte[] content;
    private int pageCount;
    private String isbn;
    private String genre;
    private String author;
    private Date publishYear;
    private String publisher;
    private byte[] image;
    
    public Book() {
        super();
    }
    
    public Book(String name) {
        super(name);
    }

    public Book(String name, int id, int pageCount, String isbn, String genre, String author, Date publishYear, String publisher) {
        super();
        this.name = name;
        this.id = id;
        this.pageCount = pageCount;
        this.isbn = isbn;
        this.genre = genre;
        this.author = author;
        this.publishYear = publishYear;
        this.publisher = publisher;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Date publishYear) {
        this.publishYear = publishYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    public String getFormattedDate() {
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
    return dateFormat.format(publishYear);
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    
    public void fillPdfContent() {
        String query = "SELECT `content` FROM `book` WHERE `id` = " + this.id + " LIMIT 0,1;";
        Connection conn = Database.getConnection();
        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                ) {
            if (rs.next())
                this.content = rs.getBytes("content");
        } catch (SQLException ex) {
            this.content = new byte[0];
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
