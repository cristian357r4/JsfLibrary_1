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
    private Books books = new Books();

    private SearchType searchType;
    private String searchString;
    private static Map<String, SearchType> searchList = new HashMap<String, SearchType>();

    private static Character[] russianLetters;

    static {
        russianLetters = new Character[33];

        int i = 0;
        for (char c = 'А'; c <= 'Я'; c++) {
            russianLetters[i++] = c;
        }
    }
    
    private int booksOnPage = 3;
    private int startPosition = 0;
    private int endPosition = 3;
    
    private int totalBookCount;
    
    private int selectedPageNumber;
    
    Integer[] pageNumbers;

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

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public Map<String, SearchType> getSearchList() {
        return searchList;
    }

    public int getBooksOnPage() {
        return booksOnPage;
    }

    public void setBooksOnPage(int booksOnPage) {
        this.booksOnPage = booksOnPage;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public int getTotalBookCount() {
        return totalBookCount;
    }
    
    public ArrayList<Book> getCurrentBookList() {
        return currentBookList;
    }

    public Integer[] getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(Integer[] pageNumbers) {
        this.pageNumbers = pageNumbers;
    }
    
    public void setPagenumbers(int count) {
        this.pageNumbers = new Integer[count];
        
        for (int i = 0; i < count; i++) {
            this.pageNumbers[i] = i + 1;
        }
    }
    
    public void setPageNumbers() {
        int count = (int) Math.ceil(1.0 * this.totalBookCount / this.booksOnPage);
        setPagenumbers(count);
    }

    public int getSelectedPageNumber() {
        return selectedPageNumber;
    }
    
    

    public void fillBooksAll() {
        this.currentBookList = books.getBookList(getLimits());
        
        this.totalBookCount = currentBookList.size();
       
        SearchController.this.setPageNumbers();
    }

    public void fillBooksByGenre() {
        Map<String, String> params = getParams();
        long id = Long.valueOf(params.get("genre_id"));

        this.currentBookList = books.getBooksByGenre(id);
        
        this.totalBookCount = currentBookList.size();
        
        SearchController.this.setPageNumbers();
    }

    public void fillBooksByLetter() {
        String letter = getParams().get("search_letter");

        this.currentBookList = books.getBooksByLetter(letter);
        
        this.totalBookCount = currentBookList.size();
        
        SearchController.this.setPageNumbers();
    }

    public void fillBooksBySearch() {
        if (searchString == null || "".equals(searchString)) {
            fillBooksAll();
        } else {
            this.currentBookList = books.getBooksBySearch(searchString, searchType);
        }
        
        this.totalBookCount = currentBookList.size();
        
        SearchController.this.setPageNumbers();
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
    
    public void selectPage() {
        this.selectedPageNumber = Integer.valueOf(getParams().get("page_number"));
    }
    
    private int[] getLimits() {
        int[] result;
        
        if (startPosition < 0 || endPosition < 0)
            result = null;
        else {
            result = new int[2];
            
            result[0] = startPosition;
            result[1] = endPosition;
        }
        
        return result;
    }
}
