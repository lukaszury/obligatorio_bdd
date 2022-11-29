/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.SQLException;
import model.DB;
import model.RecuperarInterface;

/**
 *
 * @author lukas
 */
public class RecuperarController {
    private RecuperarInterface ui;
    private DB db;
    
    public RecuperarController(RecuperarInterface ui) throws SQLException
    {
        this.ui = ui;
        this.db = new DB();
    }
    
    public void getPregunta(){
        String pregunta = db.getPreguntaUsuario(ui.getUser(), ui.getPass());
        if(pregunta.length()>1){
            ui.showPregunta(pregunta);
        } else {
            ui.showMsg("No se encontro un usuario con esos datos");
        }
    }
    
    public void changePassword(){
        String name = ui.getUser();
        String response = ui.getResponse();
        String newPass = ui.getNewPass();
        if(db.changePassword(response, name,newPass)) {
            ui.showMsg("Su clave fue cambiada a 'asd' por favor cambiela lo antes posible");
        } else {
            ui.showMsg("Ocurrio un error, contactese con un administrador");
        }
    }
}
