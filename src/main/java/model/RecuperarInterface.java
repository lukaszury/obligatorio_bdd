/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

/**
 *
 * @author lukas
 */
public interface RecuperarInterface {
    String getUser();
    String getPass();
    String getResponse();
    String getNewPass();
    void showPregunta(String pregunta);
    void showMsg(String msg);
}
