/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.SQLException;
import java.util.List;
import model.DB;
import model.PermisosRolesAppsInterface;

/**
 *
 * @author mauri
 */
public class PermisosRolesAppsControlador {
    private DB db;
    private PermisosRolesAppsInterface ui;
    
    public PermisosRolesAppsControlador(PermisosRolesAppsInterface ui)throws SQLException{
        this.ui=ui;
        this.db = new DB();
    }
    
    public List<String> obtenerRoles()
    {
        return db.obtenerRoles();
    }
    
    public List<String> obtenerApps()
    {
        return db.obtenerApps();
    }
    
    public List<Object[]> obtenerPermisosRol(int i){
        return db.obtenerPermisosRol(i);
    }
    public List<Object[]> cargarTablaPermisosApps(){
        return db.cargarTablaPermisosApps();
    }
    public boolean quitarPermisoRol(int app_id,int rol_id,int rol_negocio_id){
        return db.quitarPermisoRol(app_id,rol_id,rol_negocio_id);
    }

    public boolean agregarPermisoRol(int app_id, int rol_id, int rol_negocio_id) {
        return db.agregarPermisoRol(app_id,rol_id,rol_negocio_id);
    }
    
}
