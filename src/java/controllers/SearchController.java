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

    private int booksOnPage = 2;
    private int startPosition = 0;
    private int endPosition = 0;

    private int totalBookCount;
    private String findedBooksCount;
    
    private long selectedGenreId;
    private char selectedLetter;

    private int selectedPageNumber;

    Integer[] pageNumbers;

    private String lastQuery;
    private int lastGenreId;
    private String lastLetter;
    private String lastSearchString;

    /**
     * Creates a new instance of SearchController
     */
    public SearchController() {
        ResourceBundle bundle = ResourceBundle.getBundle("nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        searchList.put(bundle.getString("author_name"), SearchType.AUTHOR);
        searchList.put(bundle.getString("book_name"), SearchType.TITLE);
        this.totalBookCount = -1;
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
        this.totalBookCount = books.getLastRowCount();
        return totalBookCount;
    }

    public void setTotalBookCount(int totalBookCount) {
        this.totalBookCount = totalBookCount;
        this.findedBooksCount = "найдено книг: " + this.totalBookCount;
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

    public String getLastQuery() {
        return lastQuery;
    }

    public void setLastQuery(String lastQuery) {
        this.lastQuery = lastQuery;
    }

    public int getLastGenreId() {
        
        this.lastLetter = null;
        this.lastSearchString = null;
        
        if (getParams().get("genre_id") != null) {
            
            int tempId = Integer.valueOf(getParams().get("genre_id"));
            if (tempId != lastGenreId)
                setDefaultParams();
            
            lastGenreId = tempId;
        }
        return lastGenreId;
    }

    public String getLastLetter() {
        
        this.lastGenreId = -1;
        this.lastSearchString = null;
        
        
        if (getParams().get("search_letter") != null) {
            
            if (!getParams().get("search_letter").equals(lastLetter))
                setDefaultParams();
            
            lastLetter = getParams().get("search_letter");
        }
        return lastLetter;
    }

    public String getLastSearchString() {
        
        this.lastGenreId = -1;
        this.lastLetter = null;
        
        
        if (this.searchString != null) {
            
            if (!this.searchString.equals(lastSearchString))
                setDefaultParams();
            
            lastSearchString = searchString;
        }        
        
        return lastSearchString;
    }

    public String getFindedBooksCount() {
        return findedBooksCount;
    }

    public void setFindedBooksCount(String findedBooksCount) {
        this.findedBooksCount = findedBooksCount;
    }

    public long getSelectedGenreId() {
        return selectedGenreId;
    }

    public void setSelectedGenreId(long selectedGenreId) {
        this.selectedGenreId = selectedGenreId;
    }

    public char getSelectedLetter() {
        return selectedLetter;
    }

    public void setSelectedLetter(char selectedLetter) {
        this.selectedLetter = selectedLetter;
    }

    
    

//----------------------------------------------------------------------------    
    public void fillBooksAll() {
        this.currentBookList = books.getBookList(getLimits());

//        this.totalBookCount = books.getLastRowCount();
//       
//        this.setPageNumbers();
    }

    public void fillBooksByGenre() {
        long id = getLastGenreId();
        
        setSelectedGenreId(id);

        this.currentBookList = books.getBooksByGenre(id, getLimits());

        setTotalBookCount(books.getLastRowCount());

        this.setPageNumbers();

        setLastQuery("byGenre");
    }

    public void fillBooksByLetter() {
        String letter = getLastLetter();
        
        setSelectedLetter(letter.charAt(0));

        this.currentBookList = books.getBooksByLetter(letter, getLimits());

        setTotalBookCount(books.getLastRowCount());

        this.setPageNumbers();

        setLastQuery("byLetter");
    }

    public void fillBooksBySearch() {
        if (getLastSearchString() == null || "".equals(getLastSearchString())) {
            fillBooksAll();
        } else {
            this.currentBookList = books.getBooksBySearch(searchString, searchType, getLimits());
        }

        setTotalBookCount(books.getLastRowCount());

        SearchController.this.setPageNumbers();

        setLastQuery("bySearch");
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
        this.startPosition = booksOnPage * (selectedPageNumber - 1);
//        this.endPosition = startPosition + booksOnPage;
        switch (getLastQuery()) {
            case "byLetter":
                fillBooksByLetter();
                break;
            case "byGenre":
                fillBooksByGenre();
                break;
            case "bySearch":
                fillBooksBySearch();
                break;
            default:
                fillBooksAll();
                break;
        }
    }

    private int[] getLimits() {
        int[] result;

        if (startPosition < 0 || endPosition < 0) {
            result = null;
        } else {
            result = new int[2];
            endPosition = /*startPosition +*/ booksOnPage;
            result[0] = startPosition;
            result[1] = endPosition;
        }

        return result;
    }
    
    private void setDefaultParams() {
        this.selectedPageNumber = 1;
        this.startPosition = 0;
        setSelectedGenreId(-1);
        setSelectedLetter(' ');
    }
    
}
