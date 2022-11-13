/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.SQLException;
import model.DB;
import model.LoginInterface;

/**
 *
 * @author lukas
 */
public class LoginController {
    private LoginInterface ui;
    private DB db;
    
    public LoginController(LoginInterface ui)
    {
        this.ui = ui;
    }
    
    public void ingresar() throws SQLException
    {
        db = new DB();
        String user = ui.getUser();
        String pass = ui.getPass();
        if(db.verificarUsuario(user, pass)){
            ui.mostrarMsg("Ingreso");
        } else {
            ui.mostrarMsg("No ingreso :(");
        }
    }
    
}
