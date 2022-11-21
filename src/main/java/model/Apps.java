/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ignacio
 */
public class Apps {

    private DB db;

    public Apps() {
        try {
            this.db = new DB();
        } catch (SQLException ex) {
            Logger.getLogger(Apps.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String> getUserApps(int user_id) {
        ArrayList<String> resultado = new ArrayList<>();
        try {
            Connection conn = db.getConnection();
            Statement stm = conn.createStatement();
            String query = String.format("SELECT * FROM aplicativos");
            ResultSet rs;
            rs = stm.executeQuery(query);
            while(rs.next()) {
                resultado.add(rs.getString("nombreapp"));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Apps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
}
