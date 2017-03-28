/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IT10
 */
public class BookImages extends EntityList<byte[]>{

    /**
     * Creates a new instance of BookImages
     */
    public BookImages() {
    }
    
    private ArrayList<byte[]> imageList;

    public ArrayList<byte[]> getImageList(String query) {
        
        this.imageList = getList(query);
        
        return this.imageList;
    }
    
    @Override
    public byte[] getNewInstance(ResultSet resultSet) {
        byte[] bytes = new byte[0];

        try {
            bytes = resultSet.getBytes("image");
        } catch (SQLException ex) {
            Logger.getLogger(BookImages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return bytes;
    }
}
