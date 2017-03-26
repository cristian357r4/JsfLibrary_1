/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import enums.SearchType;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

/**
 *
 * @author Admin
 */
@Named(value = "searchController")
@SessionScoped
public class SearchController implements Serializable {

    private SearchType searchType;
    private static Map<String, SearchType> searchList = new HashMap<String, SearchType>();
    
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
    
    public Map<String, SearchType> getSearchList() {
        return searchList;
    }
}
