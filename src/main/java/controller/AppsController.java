/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.Apps;
import model.DB;
import view.FrmAplicativos;

/**
 *
 * @author Ignacio
 */
public class AppsController {

    private FrmAplicativos ui;
    private DB db;

    public AppsController(FrmAplicativos ui) {
        this.ui = ui;
    }
    
    public void loadCbxApps(int user_id) {
        Apps apps = new Apps();
        ArrayList<String> data = apps.getUserApps(user_id);
        for (String s : data) {
            ui.addCbxAppsItem(s);
        }
    }
}
