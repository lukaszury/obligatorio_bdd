/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.SQLException;
import model.DB;
import model.LoginInterface;
import model.UserSession;
import view.FrmAplicativos;

/**
 *
 * @author lukas
 */
public class LoginController {
    private LoginInterface ui;
    private DB db;
    public boolean isSuper;
    
    public LoginController(LoginInterface ui) throws SQLException
    {
        this.ui = ui;
        this.db = new DB();
    }
    
    public boolean ingresar(UserSession userSession) throws SQLException
    {
        String user = ui.getUser();
        String pass = ui.getPass();
        return db.verificarUsuario(user, pass, userSession);
    }
    
    public boolean isSuper(UserSession userSession)
    {
        return db.isUserAllowed(userSession.getUser_id());
    }
    
}
