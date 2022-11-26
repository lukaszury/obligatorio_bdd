/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.SQLException;
import java.util.List;
import model.DB;
import model.ManejoSolicitudInterface;

/**
 *
 * @author mauri
 */
public class ManejoSolicitudController {
    private DB db;
    private ManejoSolicitudInterface ui;
    
    public ManejoSolicitudController(ManejoSolicitudInterface ui)throws SQLException {
        this.ui=ui;
        this.db = new DB();
    }
    
    
    public List<Object[]> obtenerPendientes(){
        return db.obtenerPendientes();
    }
    
    public boolean modificarPermiso(int persona_id,int rol_id,int app_id,String modificacion){
        return db.modificarPermiso(persona_id,rol_id,app_id,modificacion);
    }
    
    
}
