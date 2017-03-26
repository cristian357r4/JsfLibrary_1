/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import db.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 *
 * @author IT10
 */
public abstract class EntityList<T> {

    protected Connection conn;
    
    ArrayList<T> resultList;
    
    public ArrayList<T> getList(String table, String criteria, String order, String... fields) {
        String query = getQuery(table, criteria, order, fields);
        return getList(query);
    }
    
    public ArrayList<T> getList(String query) {
        resultList = new ArrayList<>();
        conn = Database.getConnection();
        
        try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                ) {
            while (rs.next()) {
                T instance = getNewInstance(rs);
                resultList.add(instance);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EntityList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultList;        
    }
    
    public abstract T getNewInstance(ResultSet resultSet);

    protected String getQuery(String table, String criteria, String order, String... fields) {
        StringBuilder result = new StringBuilder("SELECT");
        if (fields[0].equals("*")) {
            result.append(" * ");
        } else {
            int len = fields.length - 1;
            for (int i = 0; i < len; i++) {
                result.append("`" + fields[i] + "`, ");
            }
            result.append("`" + fields[len] + "` ");
        }

        result.append("FROM `" + table + "` ");

        if (criteria != null && !"".equals(criteria)) {
            result.append("WHERE " + criteria + " ");
        }

        if (order != null && !"".equals(order)) {
            result.append("ORDER BY `" + order + "`");
        }

        return result.toString();
    }

    public void closeConnection() {
        try {
            if (conn != null) {
               conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(EntityList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
