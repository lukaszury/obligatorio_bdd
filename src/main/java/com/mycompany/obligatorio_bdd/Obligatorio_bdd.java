/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.obligatorio_bdd;

import helper.HashHelper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import model.DB;
import view.FrmLogin;
import view.FrmManejoSolicitud;

/**
 *
 * @author lukas
 */
public class Obligatorio_bdd {

    public static void main(String[] args) throws SQLException {
        FrmManejoSolicitud frm = new FrmManejoSolicitud();
        frm.setVisible(true);
        DB db = new DB();
        HashHelper hh = new HashHelper();
        System.out.println(hh.crypt("asd", "lucas"));
        
//        Connection conn = null;
//        
//        String url = "jdbc:mariadb://localhost:3306/obligatorio";
//        String db_user = "lukas";
//        String db_pass = "q1w2e3r4";
//        
//        try{
//            conn = DriverManager.getConnection(url,db_user,db_pass);
//            System.out.println("Conectado");
//            Statement stm = conn.createStatement();
//            stm.executeUpdate("CREATE TABLE  sexo ( "
//                     +"preg_id int NOT NULL AUTO_INCREMENT,"
//                     +"pregunta TEXT NOT NULL, "
//                     +"PRIMARY KEY(preg_id))" );
//            stm.close();
//            conn.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
