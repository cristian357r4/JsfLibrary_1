/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author IT10
 */
@FacesValidator("beans.validators.LoginValidator")
public class LoginValidator implements Validator{

    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
        
        ResourceBundle bundle = ResourceBundle.getBundle("nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        
        try {
            
            String newValue = arg2.toString();
            
            if (newValue.length() < 5) {
                throw new IllegalArgumentException(bundle.getString("login_length_error"));
            }
            
            if (!Character.isLetter(newValue.charAt(0))) {
                throw new IllegalArgumentException(bundle.getString("first_letter_error"));
            }
            
            if (getTestArray().contains(newValue)) {
                throw new IllegalArgumentException(bundle.getString("used_value"));
            }
            
        } catch (IllegalArgumentException argumentException) {
            FacesMessage message = new FacesMessage(argumentException.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
    
    private ArrayList<String> getTestArray() {
        ArrayList<String> result = new ArrayList<>();
        
        result.add("username");
        result.add("login");
        
        return result;
    }
    
}
