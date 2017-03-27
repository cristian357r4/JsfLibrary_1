/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.Book;
import enums.SearchType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

/**
 *
 * @author Admin
 */

public class SearchController implements Serializable {
    private ArrayList<Book> currentBookList;
    private Books books;

    private SearchType searchType;
    private static Map<String, SearchType> searchList = new HashMap<String, SearchType>();
    
    private static Character[] russianLetters;
    
    static {
        russianLetters = new Character[33];
        
        int i = 0;
        for (char c = 'А'; c <= 'Я'; c++) {
            russianLetters[i++] = c;
        }
    }
    
    /**
     * Creates a new instance of SearchController
     */
    public SearchController() {
        ResourceBundle bundle = ResourceBundle.getBundle("nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        searchList.put(bundle.getString("author_name"), SearchType.AUTHOR);
        searchList.put(bundle.getString("book_name"), SearchType.TITLE);
    }
    
    public SearchType getSearchType() {
        return searchType;
    }

    public void setSearchType(SearchType searchType) {
        this.searchType = searchType;
    }
    
    public Map<String, SearchType> getSearchList() {
        return searchList;
    }

    public ArrayList<Book> getCurrentBookList() {
        return currentBookList;
    }
    
    public void fillBooksByGenre() {
        Map<String, String> params = getParams();
        long id = Long.valueOf(params.get("genre_id"));
        if (this.books == null)
            this.books = new Books();
        this.currentBookList = books.getBooksByGenre(id);
    }
    
    public void fillBooksByLetter() {
        String letter = getParams().get("search_letter");
        if (this.books == null)
            this.books = new Books();
        this.currentBookList = books.getBooksByLetter(letter);
    }

    public Character[] getRussianLetters() {
        return russianLetters;
    }
    
    private Map<String, String> getParams() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    }
    
    public byte[] getImage(int id) {
        return this.currentBookList.get(id).getImage();
    }
}
