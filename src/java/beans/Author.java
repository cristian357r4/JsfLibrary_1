/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author IT10
 */
public class Author extends Item {
    private Date birthday;
    
    public Author() {
        super();
    }
    
    public Author(String name) {
        super(name);
    }
    
    public Date getBirthday() {
        return birthday;
    }
    
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public String getFormattedDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.YYYY");
        return dateFormat.format(birthday);
    }

}
