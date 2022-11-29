/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

/**
 *
 * @author lukas
 */
public interface RegistrarInterface {
    String getNombre();
    String getApellido();
    String getPass();
    String getDireccion();
    String getCiudad();
    String getDepartamento();
    int getPregunta();
    int getApp();
    String getRespuesta();
    void showMsg(String msg);
}
