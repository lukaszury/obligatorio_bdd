/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import model.DB;
import model.RegistrarInterface;

/**
 *
 * @author lukas
 */
public class RegistrarController {
    private RegistrarInterface ui;
    private DB db;
    
    public RegistrarController(RegistrarInterface ui) throws SQLException
    {
        this.ui = ui;
        this.db = new DB();
    }
    
    public void registrar()
    {
        String nombre = ui.getNombre();
        String apellido = ui.getApellido();
        String pass = ui.getPass();
        String direccion = ui.getDireccion();
        String ciudad = ui.getCiudad();
        String departamento = ui.getDepartamento();
        int rol = ui.getApp();
        int pregunta = ui.getPregunta();
        String respuesta = ui.getRespuesta();
        if(db.registrarUsuario(nombre, apellido, direccion, ciudad, departamento, pass, rol, pregunta, respuesta)){
            ui.showMsg("Su solicitud fue creada exitosamente, un administrador la evaluara en breve");
        } else {
            ui.showMsg("No solicitud no pudo ser creada, intentelo nuevamente mas tarde");
        }
    }
    
    public ArrayList<String> obtenerPreguntas()
    {
        ArrayList<String> roles = db.obtenerPreguntas();
        return roles;
    }
    
    public ArrayList<String> obtenerRoles()
    {
        ArrayList<String> roles = db.obtenerRoles();
        return roles;
    }
    
    public ArrayList<String> obtenerApps()
    {
        ArrayList<String> apps = db.obtenerApps();
        return apps;
    }
}
