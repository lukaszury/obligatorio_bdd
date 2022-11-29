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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
        crearViewAplicativosUsuario();
        crearViewMenusAutorizadosUsuario();
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
    
    public boolean changePassword(String response,String name,String newPass){
        boolean changed = false;
        String hashres = hashHelper.crypt(response, name);
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
