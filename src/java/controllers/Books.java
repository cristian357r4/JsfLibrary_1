/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Book;
import enums.SearchType;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author Admin
 */
@Named(value = "books")
@ApplicationScoped
public class Books extends EntityList<Book> {
    
    private int QUERY_START_POSITION = 0;
    private int QUERY_COUNT_LIMIT = 5;
    
    private String lastQuery;

    /**
     * Creates a new instance of Books
     */
    public Books() {
    }
    
    private ArrayList<Book> bookList;
    private ArrayList<byte[]> imageList;
    
    public ArrayList<Book> getBookList(int ... limits) {
        String query = "SELECT b.id, b.name, b.content, b.isbn, b.page_count, b.publish_year, b.image, b.descr, \n" +
        "p.name AS publisher, a.fio AS author, g.name AS genre\n" +
        "FROM \n" +
        "`book` AS b INNER JOIN `author` AS a ON b.author_id=a.id\n" +
        "INNER JOIN `genre` AS g on b.genre_id=g.id\n" +
        "INNER JOIN `publisher` AS p ON b.publisher_id=p.id\n";
        
        query += addQueryLimit(limits);
        
        lastQuery = query;
        
        if (bookList == null)
            bookList = getList(query);
        
        return bookList;
    }
    
    public ArrayList<Book> getBooksByGenre(long id, int ... limits) {
        String query = "SELECT b.id, b.name, b.content, b.isbn, b.page_count, b.publish_year, b.image, b.descr, \n" +
        "p.name AS publisher, a.fio AS author, g.name AS genre\n" +
        "FROM \n" +
        "`book` AS b INNER JOIN `author` AS a ON b.author_id=a.id\n" +
        "INNER JOIN `genre` AS g on b.genre_id=g.id\n" +
        "INNER JOIN `publisher` AS p ON b.publisher_id=p.id\n" +
        "WHERE\n" +
        "b.genre_id=%d\n" +
        "ORDER BY b.name\n";
        
        query += addQueryLimit(limits);
        
        lastQuery = query;
        
        return getList(String.format(query, id));
    }
    
    public ArrayList<Book> getBooksByLetter(String letter, int ... limits) {
        String query = "SELECT b.id, b.name, b.content, b.isbn, b.page_count, b.publish_year, b.image, b.descr, \n" +
        "p.name AS publisher, a.fio AS author, g.name AS genre\n" +
        "FROM \n" +
        "`book` AS b INNER JOIN `author` AS a ON b.author_id=a.id\n" +
        "INNER JOIN `genre` AS g on b.genre_id=g.id\n" +
        "INNER JOIN `publisher` AS p ON b.publisher_id=p.id\n" +
        "WHERE\n" +
        "UPPER(SUBSTR(b.name, 1, 1)) = '%s' \n" +
        "ORDER BY b.name\n";
        
        query += addQueryLimit(limits);
        
        query = String.format(query, letter.toUpperCase());
        
        lastQuery = query;
        
        return getList(query);
    }
    
    public ArrayList<Book> getBooksBySearch(String search, SearchType searchType, int ... limits) {
        StringBuilder query = new StringBuilder("SELECT b.id, b.name, b.content, b.isbn, b.page_count, b.publish_year, b.image, b.descr, \n" +
        "p.name AS publisher, a.fio AS author, g.name AS genre\n" +
        "FROM \n" +
        "`book` AS b INNER JOIN `author` AS a ON b.author_id=a.id\n" +
        "INNER JOIN `genre` AS g on b.genre_id=g.id\n" +
        "INNER JOIN `publisher` AS p ON b.publisher_id=p.id\n" +
        "WHERE\n");
        
        if (searchType == SearchType.AUTHOR) {
            query.append("LOWER(a.fio) like '%" + search.toLowerCase() + "%' ");
        } else if (searchType == SearchType.TITLE) {
            query.append("b.name like '%" + search.toLowerCase() + "%' ");
        }
        
        query.append("ORDER BY b.name");
        
        query.append(addQueryLimit(limits));
        
        lastQuery = query.toString();
        
        return getList(query.toString());
    }

    public int getQUERY_START_POSITION() {
        return QUERY_START_POSITION;
    }

    public int getQUERY_COUNT_LIMIT() {
        return QUERY_COUNT_LIMIT;
    }

    public ArrayList<byte[]> getImageList(int startPosition, int countLimit) {
        
        int startPredicat = lastQuery.toUpperCase().indexOf(" FROM ") + 1;
        int endPredicat = lastQuery.toUpperCase().indexOf(" LIMIT ");
        
        String predicat = lastQuery.substring(startPredicat, endPredicat);
        
        String limits = "LIMIT " + startPosition + ", " + countLimit + ";";
        
        String imageQuery = "SELECT `book.image` " + predicat + " " + limits;
        
        BookImages bookImages = new BookImages();
        
        this.imageList = bookImages.getImageList(imageQuery);
        
        return imageList;
    }

    @Override
    public Book getNewInstance(ResultSet resultSet) {
        Book book = new Book();
        try {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            int pageCount = resultSet.getInt("page_count");
            String isbn = resultSet.getString("isbn");
            String genre = resultSet.getString("genre");
            String author = resultSet.getString("author");
            int publishYear = resultSet.getInt("publish_year");
            String publisher = resultSet.getString("publisher");
            byte[] image = resultSet.getBytes("image");
            String description = resultSet.getString("descr");
            
            book.setId(id);
            book.setName(name);
            book.setPageCount(pageCount);
            book.setIsbn(isbn);
            book.setGenre(genre);
            book.setAuthor(author);
            book.setPublishYear(publishYear);
            book.setPublisher(publisher);
            book.setImage(image);
            book.setDescription(description);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }
    
    private String addQueryLimit(int ... limits) {
        String result = "LIMIT ";
        
        if (limits == null || limits.length == 0)
            result += + QUERY_START_POSITION + ", " + QUERY_COUNT_LIMIT + ";";
        else if (limits.length == 2)
            result += limits[0] + ", " + limits[1] + ";";
        
        return result;
    }
    
}
