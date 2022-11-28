/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import helper.HashHelper;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lukas
 */
public class DB {

    private String url = "jdbc:mariadb://localhost:3306/obligatorio";
    private String db_user = "root";
    private String db_pass = "root";
    private Connection conn = null;
    private HashHelper hashHelper = null;

    public DB() throws SQLException {
        conn = DriverManager.getConnection(url, db_user, db_pass);
        cargarDatos();
        conn.close();
        hashHelper = new HashHelper();
    }

    private void cargarDatos() {
        cargarPreguntas();
        cargarRolesNegocio();
        cargarAplicativos();
        cargarAplicativosMenu();
        cargarRolesAplicativos();
        cargarRolesAplicativosMenu();
        cargarRolesNegocioAplicativos();
        cargarPersonas();
        cargarPersonasPreguntas();
        cargarPermisos();
        solicitudesView();
        negocioAplicativo();
        AppsRolesView();
    }
    // se generan todas las tablas y views en caso de que no existieran en la base
    private void cargarPreguntas() {
        try {
            System.out.println("Preguntas");
            if (!existeTabla("preguntas")) {
                Statement stm = conn.createStatement();
                stm.executeUpdate("CREATE TABLE  preguntas ( "
                        + "preg_id int NOT NULL AUTO_INCREMENT,"
                        + "pregunta TEXT NOT NULL, "
                        + "PRIMARY KEY(preg_id))");
                stm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarRolesNegocio() {
        try {
            System.out.println("Roles Negocio");
            if (!existeTabla("roles_negocio")) {
                Statement stm = conn.createStatement();
                stm.executeUpdate("CREATE TABLE  roles_negocio ( "
                        + "rol_neg_id int NOT NULL AUTO_INCREMENT,"
                        + "descripcion_rol_neg TEXT NOT NULL, "
                        + "PRIMARY KEY(rol_neg_id))");
                stm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarAplicativos() {
        try {
            System.out.println("Aplicativos");
            if (!existeTabla("aplicativos")) {
                Statement stm = conn.createStatement();
                stm.executeUpdate("CREATE TABLE  aplicativos ( "
                        + "app_id int NOT NULL AUTO_INCREMENT,"
                        + "nombreapp TEXT NOT NULL, "
                        + "PRIMARY KEY(app_id))");
                stm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarAplicativosMenu() {
        try {
            System.out.println("Aplicativos Menu");
            if (!existeTabla("aplicativos_menu")) {
                Statement stm = conn.createStatement();
                stm.executeUpdate("CREATE TABLE  aplicativos_menu ( "
                        + "app_id int,"
                        + "menu_id int NOT NULL AUTO_INCREMENT,"
                        + "descripcion_menu TEXT NOT NULL, "
                        + "PRIMARY KEY(menu_id),"
                        + "FOREIGN KEY(app_id) REFERENCES aplicativos(app_id))");
                stm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarRolesAplicativos() {
        try {
            System.out.println("Roles Aplicativos");
            if (!existeTabla("roles_aplicativos")) {
                Statement stm = conn.createStatement();
                stm.executeUpdate("CREATE TABLE  roles_aplicativos ( "
                        + "app_id int,"
                        + "rol_id int NOT NULL AUTO_INCREMENT,"
                        + "descripcion_rol TEXT NOT NULL, "
                        + "PRIMARY KEY(rol_id),"
                        + "FOREIGN KEY(app_id) REFERENCES aplicativos(app_id))");
                stm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarRolesAplicativosMenu() {
        try {
            System.out.println("Roles Aplicativos Menu");
            if (!existeTabla("roles_aplicativos_menu")) {
                Statement stm = conn.createStatement();
                stm.executeUpdate("CREATE TABLE  roles_aplicativos_menu ( "
                        + "app_id int,"
                        + "rol_id int,"
                        + "menu_id int NOT NULL AUTO_INCREMENT,"
                        + "descripcion_menu TEXT NOT NULL, "
                        + "PRIMARY KEY(menu_id),"
                        + "FOREIGN KEY(app_id) REFERENCES aplicativos(app_id),"
                        + "FOREIGN KEY(rol_id) REFERENCES roles_aplicativos(rol_id))");
                stm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarRolesNegocioAplicativos() {
        try {
            System.out.println("Roles Negocio Aplicativos");
            if (!existeTabla("roles_negocio_aplicativos")) {
                Statement stm = conn.createStatement();
                stm.executeUpdate("CREATE TABLE  roles_negocio_aplicativos ( "
                        + "rol_neg_id int,"
                        + "app_id int,"
                        + "rol_id int,"
                        + "PRIMARY KEY(rol_neg_id,app_id,rol_id),"
                        + "FOREIGN KEY(rol_neg_id) REFERENCES roles_negocio(rol_neg_id),"
                        + "FOREIGN KEY(app_id) REFERENCES aplicativos(app_id),"
                        + "FOREIGN KEY(rol_id) REFERENCES roles_aplicativos(rol_id))");
                stm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarPersonas() {
        try {
            System.out.println("Personas");
            if (!existeTabla("personas")) {
                Statement stm = conn.createStatement();
                stm.executeUpdate("CREATE TABLE  personas ( "
                        + "user_id int NOT NULL AUTO_INCREMENT,"
                        + "nombres TEXT NOT NULL,"
                        + "apellidos TEXT NOT NULL,"
                        + "direccion TEXT NOT NULL,"
                        + "ciudad TEXT NOT NULL,"
                        + "departamento TEXT NOT NULL,"
                        + "hashpwd TEXT NOT NULL UNIQUE,"
                        + "PRIMARY KEY(user_id));");
                stm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarPersonasPreguntas() {
        try {
            System.out.println("Personas Preguntas");
            if (!existeTabla("personas_preguntas")) {
                Statement stm = conn.createStatement();
                stm.executeUpdate("CREATE TABLE  personas_preguntas ( "
                        + "user_id int NOT NULL,"
                        + "preg_id int NOT NULL,"
                        + "respuesta TEXT NOT NULL,"
                        + "FOREIGN KEY(user_id) REFERENCES personas(user_id),"
                        + "FOREIGN KEY(preg_id) REFERENCES preguntas(preg_id))");
                stm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarPermisos() {
        try {
            System.out.println("Permisos");
            if (!existeTabla("permisos")) {
                Statement stm = conn.createStatement();
                stm.executeUpdate("CREATE TABLE  permisos ( "
                        + "user_id int,"
                        + "app_id int,"
                        + "rol_neg_id int,"
                        + "fecha_solicitud DATE NOT NULL, "
                        + "fecha_autorizacion DATE,"
                        + "estado TEXT NOT NULL,"
                        + "FOREIGN KEY(user_id) REFERENCES personas(user_id),"
                        + "FOREIGN KEY(app_id) REFERENCES aplicativos(app_id),"
                        + "FOREIGN KEY(rol_neg_id) REFERENCES roles_negocio(rol_neg_id));");
                stm.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void solicitudesView(){
        try{
           
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            if(!existeTabla("solicitudes_view")){
                stm.executeQuery("CREATE VIEW solicitudes_view AS SELECT personas.nombres, roles_negocio.descripcion_rol_neg,"
                                            + "aplicativos.nombreapp,personas.user_id, roles_negocio.rol_neg_id,"
                                            + "aplicativos.app_id\n" +
                                            "FROM personas\n" +
                                            "join permisos\n" +
                                            "ON personas.user_id = permisos.user_id\n" +
                                            "JOIN aplicativos\n" +
                                            "ON aplicativos.app_id=permisos.app_id\n" +
                                            "join roles_negocio\n" +
                                            "ON roles_negocio.rol_neg_id = permisos.rol_neg_id\n" +
                                            "WHERE estado ='PENDIENTE';");
            } 
        }catch (SQLException ex) {
            ex.printStackTrace();     
        }
    }
    
    private void AppsRolesView(){
        try{
           
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            if(!existeTabla("apps_roles")){
                stm.executeQuery("CREATE VIEW apps_roles AS \n" +
                                 "SELECT aplicativos.nombreapp,roles_aplicativos.descripcion_rol,"+
                                 "aplicativos.app_id,roles_aplicativos.rol_id\n" +
                                 "FROM roles_aplicativos\n" +
                                 "JOIN aplicativos\n" +
                                 "ON roles_aplicativos.app_id=aplicativos.app_id;");
            } 
        }catch (SQLException ex) {
            ex.printStackTrace();     
        }
    }
    
    private void negocioAplicativo(){
             try{
           
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            if(!existeTabla("negocio_aplicativo")){
                stm.executeQuery("CREATE VIEW negocio_aplicativo AS SELECT roles_negocio_aplicativos.rol_neg_id,"
                        + " roles_aplicativos.rol_id,roles_aplicativos.descripcion_rol,aplicativos.app_id,"
                        + "aplicativos.nombreapp\n" 
                        +"FROM roles_negocio_aplicativos\n" 
                        +"join roles_aplicativos\n"
                        +"ON roles_aplicativos.rol_id = roles_negocio_aplicativos.rol_id "
                        +"AND roles_aplicativos.app_id =roles_negocio_aplicativos.app_id\n"
                        +"JOIN aplicativos\n"
                        +"ON aplicativos.app_id=roles_aplicativos.app_id;");
            } 
        }catch (SQLException ex) {
            ex.printStackTrace();     
        }
    }

    public boolean verificarUsuario(String user, String pass) {
        String hashpwd = hashHelper.crypt(pass, user);
        try {
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            String query = String.format("SELECT (count(*) > 0) AS usuario FROM personas WHERE (nombres = '%s' AND hashpwd = '%s')", user, hashpwd);
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                boolean encontrado = rs.getBoolean(1);
                stm.close();
                conn.close();
                if (encontrado) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean registrarUsuario(String nombre, String apellido, String direccion, String ciudad, String departamento, String hashpwd, int rol, int pregunta, String respuesta) {
        int id = -1;
        boolean compleado = false;
        try {
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            hashpwd = hashHelper.crypt(hashpwd, nombre);
            String query = String.format("INSERT INTO personas (nombres, apellidos, direccion, ciudad, departamento, hashpwd) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", nombre, apellido, direccion, ciudad, departamento, hashpwd);
            stm.executeUpdate(query);
            query = String.format("SELECT user_id FROM personas WHERE hashpwd = '%s'", hashpwd);
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                id = Integer.parseInt(rs.getString(1));
                System.out.println(id);
            }
            String hashres = hashHelper.crypt(respuesta, nombre);
            System.out.println(pregunta);
            query = String.format("INSERT INTO personas_preguntas (user_id, preg_id, respuesta) VALUES (%d, %d, '%s')", id, pregunta, hashres);
            stm.executeUpdate(query);
            stm.close();
            conn.close();
            compleado = true;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return compleado;
    }

    public ArrayList<String> obtenerPreguntas() {
        ArrayList<String> data = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM preguntas");
            while (rs.next()) {
                String item = rs.getString("preg_id") + " - " + rs.getString("pregunta");
                data.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public ArrayList<String> obtenerRoles() {
        ArrayList<String> data = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM roles_negocio");
            while (rs.next()) {
                String item = rs.getString("rol_neg_id") + " - " + rs.getString("descripcion_rol_neg");
                data.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public ArrayList<String> obtenerApps() {
        ArrayList<String> data = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM aplicativos");
            while (rs.next()) {
                String item = rs.getString("app_id") + " - " + rs.getString("nombreApp");
                data.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public List<Object[]> obtenerPendientes(){
        List<Object[]> datos = new LinkedList<>();
        try {
            
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM solicitudes_view");
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnas = rsMetaData.getColumnCount();
            while (rs.next()) {
                Object[] item = new Object[columnas];
                for(int i =0; i < columnas; i++){
                    item[i]=rs.getObject(i+1);
                }
                datos.add(item);
           
            }
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datos;
    }
    
    
    //metodo para  modificar permiso con par persona,app recibido, modificacion siendo si es aprobado o no
    public boolean modificarPermiso(int persona_id,int rol_id,int app_id,String modificacion){
        
         try {
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            //se registra la hora actual
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
             LocalDateTime horaActual = LocalDateTime.now();  
            String query = String.format("UPDATE permisos\n" +
                                            "SET estado = '%s', fecha_autorizacion= '%s' \n"+
                                            "WHERE user_id= %d AND rol_neg_id = %d AND app_id = %d;", modificacion,
                                            dtf.format(horaActual),persona_id,rol_id,app_id);
            stm.executeUpdate(query);
            stm.close();
            conn.close();
            return true;
         } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
         return false;
    }
    // del view negocio_aplicativo se obtienen los datos a mostrar en el form manejo solicitud
    // se obtienen todos los permisos para el rol deseado
    public List<Object[]> obtenerPermisosRol(int rol){
        List<Object[]> datos = new LinkedList<>();
        try {
            
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            String query = String.format("SELECT negocio_aplicativo.nombreapp, negocio_aplicativo.descripcion_rol,"
                    +"negocio_aplicativo.app_id, negocio_aplicativo.rol_id, negocio_aplicativo.rol_neg_id \n" 
                    +" FROM negocio_aplicativo \n"
                    + "WHERE rol_neg_id = %d ",rol);
            ResultSet rs = stm.executeQuery(query);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnas = rsMetaData.getColumnCount();
            while (rs.next()) {
                Object[] item = new Object[columnas];
                for(int i =0; i < columnas; i++){
                    item[i]=rs.getObject(i+1);
                }
                datos.add(item);
           
            }
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datos;
        
    }
    //se obtienen todos los permisos existentes de cada rol para cada aplicativo
    public List<Object[]> cargarTablaPermisosApps(){
        List<Object[]> datos = new LinkedList<>();
        try {
            
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            String query = String.format("SELECT * FROM apps_roles");
            ResultSet rs = stm.executeQuery(query);
            ResultSetMetaData rsMetaData = rs.getMetaData();
            int columnas = rsMetaData.getColumnCount();
            while (rs.next()) {
                Object[] item = new Object[columnas];
                for(int i =0; i < columnas; i++){
                    item[i]=rs.getObject(i+1);
                }
                datos.add(item);
           
            }
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return datos;
        
    }
    //se remueve elregistro del permiso con los valores pasados por parametro de la tabla roles_negocio_aplicativo
    public boolean quitarPermisoRol(int app_id, int rol_id, int rol_negocio_id) {
       try {
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            String query = String.format("DELETE FROM roles_negocio_aplicativos\n" +
                                            "WHERE app_id= %d AND rol_id = %d AND rol_neg_id = %d;",
                                            app_id,rol_id,rol_negocio_id);
            stm.executeUpdate(query);
            stm.close();
            conn.close();
            return true;
         } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
         return false;
    }
    //se remueve elregistro del permiso con los valores pasados por parametro de la tabla roles_negocio_aplicativo
    public boolean agregarPermisoRol(int app_id, int rol_id, int rol_negocio_id) {
         try {
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            String query = String.format("INSERT INTO roles_negocio_aplicativos\n" +
                                            "values( %d,%d,%d);",
                                            rol_negocio_id,app_id,rol_id);
            stm.executeUpdate(query);
            stm.close();
            conn.close();
            return true;
         } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
         return false;
    }
    

    private boolean existeTabla(String nombre) throws SQLException {
        DatabaseMetaData dbm = conn.getMetaData();
        ResultSet tables = dbm.getTables(null, null, nombre, null);
        return tables.next();
    }

    
}
