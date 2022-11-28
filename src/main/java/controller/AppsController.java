/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import model.App;
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
    private Apps apps;

    public AppsController(FrmAplicativos ui) {
        this.ui = ui;
        this.apps = new Apps();
    }
    
    public void loadCbxApps(int user_id) {
        ArrayList<App> data = this.apps.getUserApps(user_id);
        DefaultComboBoxModel model = new DefaultComboBoxModel(data.toArray());
        ui.setCbxAppsModel(model);
    }
    
    public void loadTableWithMenuItems(int user_id, int app_id) {
        DefaultTableModel model = this.apps.getUserAppMenus(user_id, app_id);
        ui.setTableMenusModel(model);
    }
}
