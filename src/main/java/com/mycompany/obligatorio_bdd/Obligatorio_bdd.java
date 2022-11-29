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

/**
 *
 * @author lukas
 */
public class Obligatorio_bdd {

    public static void main(String[] args) throws SQLException {
        FrmLogin frm = new FrmLogin();
        frm.setVisible(true);
    }
}
