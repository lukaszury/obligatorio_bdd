/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import helper.DateHelper;
import helper.HashHelper;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
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
    private DateHelper dateHelper = null;

    public DB() throws SQLException {
        conn = DriverManager.getConnection(url, db_user, db_pass);
        cargarDatos();
        initializeData();
        conn.close();
        hashHelper = new HashHelper();
        dateHelper = new DateHelper();
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
        crearViewAplicativosUsuario();
        crearViewMenusAutorizadosUsuario();
        solicitudesView();
        negocioAplicativo();
        AppsRolesView();
    }

    private void crearViewAplicativosUsuario() {
        try {
            System.out.println("Creación view de aplicativos de usuario");
            if (!existeTabla("view_aplicativos_usuario")) {
                Statement stm = conn.createStatement();
                stm.executeUpdate("CREATE VIEW view_aplicativos_usuario AS "
                        + "SELECT a.app_id, a.nombreapp, p.estado, p.user_id "
                        + "FROM permisos p "
                        + "JOIN roles_negocio rn ON p.rol_neg_id = rn.rol_neg_id "
                        + "JOIN roles_negocio_aplicativos rna ON rna.rol_neg_id = rn.rol_neg_id "
                        + "JOIN roles_aplicativos ra ON ra.rol_id = rna.rol_id "
                        + "JOIN aplicativos a ON a.app_id = rna.app_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void crearViewMenusAutorizadosUsuario() {
        try {
            System.out.println("Creación view de menus autorizados de usuario");
            if (!existeTabla("view_aplicativos_usuario")) {
                Statement stm = conn.createStatement();
                stm.executeUpdate("CREATE VIEW view_menus_autorizados AS "
                        + "SELECT am.descripcion_menu, am.menu_id, p.estado, p.user_id, a.nombreapp, a.app_id "
                        + "FROM permisos p "
                        + "JOIN roles_negocio rn ON p.rol_neg_id = rn.rol_neg_id "
                        + "JOIN roles_negocio_aplicativos rna ON rna.rol_neg_id = rn.rol_neg_id "
                        + "JOIN roles_aplicativos ra ON ra.rol_id = rna.rol_id "
                        + "JOIN aplicativos a ON ra.app_id = a.app_id "
                        + "JOIN roles_aplicativos_menu ram ON ram.rol_id = ra.rol_id "
                        + "JOIN aplicativos_menu am ON ram.menu_id = am.menu_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                        + "PRIMARY KEY(menu_id, app_id),"
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
                        + "PRIMARY KEY(rol_id, app_id),"
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
                        + "menu_id int,"
                        + "FOREIGN KEY(app_id) REFERENCES aplicativos(app_id),"
                        + "FOREIGN KEY(rol_id) REFERENCES roles_aplicativos(rol_id),"
                        + "FOREIGN KEY(menu_id) REFERENCES aplicativos_menu(menu_id),"
                        + "PRIMARY KEY(app_id, rol_id, menu_id))");
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
                        + "PRIMARY KEY (rol_neg_id, app_id, rol_id),"
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
                        + "PRIMARY KEY(user_id))");
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
                        + "PRIMARY KEY(user_id, preg_id),"
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
                stm.executeUpdate("CREATE TABLE  permisos ("
                        + "user_id int,"
                        + "app_id int,"
                        + "rol_neg_id int,"
                        + "fecha_solicitud DATE NOT NULL,"
                        + "fecha_autorizacion DATE,"
                        + "estado TEXT NOT NULL,"
                        + "PRIMARY KEY(user_id, app_id, rol_neg_id),"
                        + "FOREIGN KEY(user_id) REFERENCES personas(user_id),"
                        + "FOREIGN KEY(app_id) REFERENCES aplicativos(app_id),"
                        + "FOREIGN KEY(rol_neg_id) REFERENCES roles_negocio(rol_neg_id))");
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

    public boolean verificarUsuario(String user, String pass, UserSession userSession) {
        String hashpwd = hashHelper.crypt(pass, user);
        try {
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            String query = String.format("SELECT * FROM personas WHERE (nombres = '%s' AND hashpwd = '%s')", user, hashpwd);
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                boolean encontrado = rs.getBoolean(1);
                stm.close();
                conn.close();
                if (encontrado) {
                    userSession.setUser_id(rs.getInt(1));
                    userSession.setNombres(rs.getString(2));
                    userSession.setApellidos(rs.getString(3));
                    userSession.setDireccion(rs.getString(4));
                    userSession.setCiudad(rs.getString(5));
                    userSession.setApartamento(rs.getString(6));
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
        boolean completado = false;
        try {
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            String salt = nombre+apellido;
            hashpwd = hashHelper.crypt(hashpwd, salt);
            String query = String.format("INSERT INTO personas (nombres, apellidos, direccion, ciudad, departamento, hashpwd) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", nombre, apellido, direccion, ciudad, departamento, hashpwd);
            stm.executeUpdate(query);
            query = String.format("SELECT user_id FROM personas WHERE hashpwd = '%s'", hashpwd);
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                id = Integer.parseInt(rs.getString(1));
                System.out.println(id);
            }
            String hashres = hashHelper.crypt(respuesta, salt);
            System.out.println(pregunta);
            query = String.format("INSERT INTO personas_preguntas (user_id, preg_id, respuesta) VALUES (%d, %d, '%s')", id, pregunta, hashres);
            stm.executeUpdate(query);
            Date date = dateHelper.dateFormatter();
            query = String.format("INSERT INTO permisos (user_id, app_id, rol_neg_id ,fecha_solicitud, fecha_autorizacion, estado) VALUES (%d, 1, %d,'%s', NULL, 'PENDIENTE')", id, rol,date);
            stm.executeUpdate(query);
            
            stm.close();
            conn.close();
            completado = true;
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return completado;
    }
    
    public String getPreguntaUsuario(String nombre, String apellido){
        String pregunta = "";
        try {
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            String query = String.format("SELECT pregunta FROM personas p JOIN personas_preguntas pp ON p.user_id = pp.user_id JOIN preguntas pr ON pp.preg_id = pr.preg_id WHERE p.nombres = '%s' AND p.apellidos = '%s';", nombre, apellido);
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                pregunta = rs.getString(1);
                System.out.println(pregunta);
            }
            stm.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pregunta;
    }

    public ArrayList<String> obtenerPreguntas() {
        ArrayList<String> data = new ArrayList<String>();
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
    
    public boolean changePassword(String response,String name,String surname,String newPass){
        boolean changed = false;
        String salt = name+surname;
        String hashres = hashHelper.crypt(response, salt);
        String hashpass = hashHelper.crypt(newPass, name);
        System.out.println(hashres);
        try {
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            String query = String.format("UPDATE personas p JOIN personas_preguntas pp ON p.user_id = pp.user_id SET p.hashpwd = '%s' WHERE pp.respuesta = '%s'" , hashpass,hashres);
            int rowsAffected = stm.executeUpdate(query);
            if(rowsAffected>0) {
                changed = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return changed;
    }
    
    public boolean isUserAllowed(int user_id)
    {
        boolean encontrado = false;
        try {
            conn = DriverManager.getConnection(url, db_user, db_pass);
            Statement stm = conn.createStatement();
            String query = String.format("SELECT p1.user_id FROM permisos p1 JOIN roles_negocio rn ON p1.rol_neg_id = rn.rol_neg_id WHERE p1.estado = 'ACTIVO' AND rn.descripcion_rol_neg = 'Administrador' AND p1.user_id = %d GROUP BY p1.user_id" , user_id);
            ResultSet rs = stm.executeQuery(query);
            if (rs.next()) {
                encontrado = rs.getBoolean(1);
                stm.close();
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encontrado;
    }

    public ArrayList<String> obtenerRoles() {
        ArrayList<String> data = new ArrayList<String>();
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
    
    private void initializeData()
    {
        try {
            if(existeTabla("permisos")){
            return;
        }
            Statement stm = conn.createStatement();
            stm.executeUpdate("INSERT INTO personas (user_id, nombres, apellidos, direccion, ciudad, departamento, hashpwd) "
                    + "VALUES (1, 'Juan', 'Alvez', 'Av. Italia 2291', 'Montevideo', 'Montevideo', 'a0ad0f9a9236e560b06eeb73733642505946277a2d536085b223e1b910758de8d031c46bb1334ddd289e3a58577e6a568b47d9744ba182b19c11ad7aa4fa67fc' )");
            stm.executeUpdate("INSERT INTO personas (user_id, nombres, apellidos, direccion, ciudad, departamento, hashpwd) "
                    + "VALUES (2, 'Luis', 'Lopez', 'Morquio 876', 'Salto', 'Salto', '0655a31ef5a7694557afdd15f6d2acc4f3b4af5981ce7b92f54ea9c8b5d7da6302f0816b69c38999be40b62fe78bbf6c9fa03c78c93602a68615a5a6d06b9e8d' )");
            stm.executeUpdate("INSERT INTO personas (user_id, nombres, apellidos, direccion, ciudad, departamento, hashpwd) "
                    + "VALUES (3, 'Rodrigo', 'Hernandez', 'Obligado 795', 'Montevideo', 'Montevideo', '7aeb22a7a7d63cc5b619b99633775201ebff42b38dd6acc3d4c1bfa1710dbf3c514f38e25861f153d3fe8fc85a51a49ad11102ef44d59e9dc2176e1665518544' )");
            stm.executeUpdate("INSERT INTO preguntas (preg_id, pregunta) VALUES (1,'Como era el nombre su primer mascota?')");
            stm.executeUpdate("INSERT INTO preguntas (preg_id, pregunta) VALUES (2,'De que cuadro deportivo es fanatico?')");
            stm.executeUpdate("INSERT INTO preguntas (preg_id, pregunta) VALUES (3,'Cual es su comida favorita?')");
            stm.executeUpdate("INSERT INTO roles_negocio (rol_neg_id, descipcion_rol_neg) VALUES (1,'Administrador')");
            stm.executeUpdate("INSERT INTO roles_negocio (rol_neg_id, descipcion_rol_neg) VALUES (2,'Cajero')");
            stm.executeUpdate("INSERT INTO roles_negocio (rol_neg_id, descipcion_rol_neg) VALUES (3,'Mesero')");
            stm.executeUpdate("INSERT INTO aplicativos (app_id, nombreapp) VALUES (1,'Restaurante')");
            stm.executeUpdate("INSERT INTO aplicativos (app_id, nombreapp) VALUES (1,'Supermercado')");
            stm.executeUpdate("INSERT INTO roles_aplicativos (app_id, rol_id, descripcion_rol) VALUES (1, 1,'Administrador de restaurante')");
            stm.executeUpdate("INSERT INTO roles_aplicativos (app_id, rol_id, descripcion_rol) VALUES (2, 2,'Cajero de supermercado')");
            stm.executeUpdate("INSERT INTO roles_aplicativos (app_id, rol_id, descripcion_rol) VALUES (1, 3,'Mesero de Restaurante')");
            stm.executeUpdate("INSERT INTO roles_aplicativos_menu (app_id, rol_id, descripcion_rol) VALUES (1, 1, 1)");
            stm.executeUpdate("INSERT INTO roles_aplicativos_menu (app_id, rol_id, descripcion_rol) VALUES (1, 1, 2)");
            stm.executeUpdate("INSERT INTO roles_aplicativos_menu (app_id, rol_id, descripcion_rol) VALUES (1, 3, 3)");
            stm.executeUpdate("INSERT INTO roles_negocio_aplicativos (rol_neg_id ,app_id, rol_id) VALUES (1, 1, 1)");
            stm.executeUpdate("INSERT INTO roles_negocio_aplicativos (rol_neg_id ,app_id, rol_id) VALUES (3, 1, 1)");
            stm.executeUpdate("INSERT INTO aplicativos_menu (app_id, menu_id, descripcion_menu) VALUES (1, 1,'Menu Restaurante')");
            stm.executeUpdate("INSERT INTO aplicativos_menu (app_id, menu_id, descripcion_menu) VALUES (2, 2,'Menu Supermercado')");
            stm.executeUpdate("INSERT INTO personas_preguntas (user_id, preg_id, respuesta) VALUES (1, 2, 'f674cbbaa5c791274b12672c93b7c32a34abebb1f2c7d537b090a6949e70a997e3f2f89a47e18c1c8694713760b8d816931562076f70d763bb46b6d1603e8ebc')");
            stm.executeUpdate("INSERT INTO personas_preguntas (user_id, preg_id, respuesta) VALUES (2, 1, '50c949c213e7a7884a56a82c93157505fec638a3d486df0046a6c2b671c4ac8d029959e9a0d25b1afc1334be570f503311b1ccb61b99d0e946a065e3947b2a72')");
            stm.executeUpdate("INSERT INTO personas_preguntas (user_id, preg_id, respuesta) VALUES (3, 3, '59454d59e2715bd812111177857d6eb0f8978c68f3c6b47a9e2d6392735b5e3988faa09dbecf3f81cc6d5d8c9687cd1de57aa27c889b18b860c205073756d2d5')");
            stm.executeUpdate("INSERT INTO permisos (user_id, app_id, rol_neg_id, fecha_solicitud, fecha_autorizacion, estado) VALUES (1, 1, 1, '2022-11-29', '2022-11-29', 'ACTIVO')");
            stm.executeUpdate("INSERT INTO permisos (user_id, app_id, rol_neg_id, fecha_solicitud, fecha_autorizacion, estado) VALUES (2, 2, 2, '2022-11-29', '2022-11-29', 'PENDIENTE')");
            stm.executeUpdate("INSERT INTO permisos (user_id, app_id, rol_neg_id, fecha_solicitud, fecha_autorizacion, estado) VALUES (3, 1, 1, '2022-11-29', '2022-11-29', 'PENDIENTE')");
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    

    private boolean existeTabla(String nombre) throws SQLException {
        DatabaseMetaData dbm = conn.getMetaData();
        ResultSet tables = dbm.getTables(null, null, nombre, null);
        return tables.next();
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, db_user, db_pass);
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
