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
import javax.swing.table.DefaultTableModel;

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

    public ArrayList<App> getUserApps(int user_id) {
        ArrayList<App> resultado = new ArrayList<>();
        try {
            Connection conn = db.getConnection();
            Statement stm = conn.createStatement();
            String query = String.format("SELECT * FROM view_aplicativos_usuario WHERE estado = 'APROBADO' AND user_id = '%s'", user_id);
            ResultSet rs;
            rs = stm.executeQuery(query);
            resultado.add(new App(0, "Seleccione un aplicativo"));
            while (rs.next()) {
                resultado.add(new App(rs.getInt("app_id"), rs.getString("nombreapp")));
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Apps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    public DefaultTableModel getUserAppMenus(int user_id, int app_id) {
        DefaultTableModel resultado = new DefaultTableModel();
        resultado.addColumn("Nombre app");
        resultado.addColumn("Menú");
        try {
            Connection conn = db.getConnection();
            Statement stm = conn.createStatement();
            String query = String.format("SELECT * FROM view_menus_autorizados WHERE estado = 'APROBADO' AND user_id = %s AND app_id = %s", user_id, app_id);
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                String[] row = new String[2];
                row[0] = rs.getString("nombreapp");
                row[1] = rs.getString("descripcion_menu");
                resultado.addRow(row);
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            Logger.getLogger(Apps.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }
}
