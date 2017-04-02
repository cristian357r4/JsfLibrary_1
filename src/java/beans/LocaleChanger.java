/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.util.Locale;
import javax.faces.context.FacesContext;

/**
 *
 * @author Admin
 */
public class LocaleChanger implements Serializable {

    /**
     * Creates a new instance of LocaleChanger
     */
    public LocaleChanger() {
        if (FacesContext.getCurrentInstance().getViewRoot() != null)
            locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        else
            locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }
    
    private Locale locale;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(String localeCode) {
        this.locale = new Locale(localeCode);
    }
    
}
