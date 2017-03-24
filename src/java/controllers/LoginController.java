/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author IT10
 */
@Named(value = "loginController")
@RequestScoped
public class LoginController {

    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
    }
    
    public String login() {
        return "books";
    }
    
}
