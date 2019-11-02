/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vending_machine;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author afar0308
 */
public class AppImplements extends App {
    
    private admin da;
    private panel_mesin ms;
    private panel_mesin mm;
    
    public AppImplements(admin da, panel_mesin ms, panel_mesin mm) {
        this.da = da;
        this.ms = ms;
        this.mm = mm;
    }

    @Override
    public void bOkClicked(ActionEvent evt) {
        if (getSelectedMesin() == 0) {
            try {
                ms.initMesin(0);
            } catch (SQLException ex) {
                Logger.getLogger(AppImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
            ubahPanel("mesin_snack", "Mesin Snack", 550, 590);
        } else if (getSelectedMesin() == 1) {
            try {
                mm.initMesin(1);
            } catch (SQLException ex) {
                Logger.getLogger(AppImplements.class.getName()).log(Level.SEVERE, null, ex);
            }
            ubahPanel("mesin_minuman", "Mesin Minuman", 550, 590);
        }
    }
    
    @Override
    public void bKeluarClicked(ActionEvent evt) {
        System.exit(0);
    }

    /*
     * Event untuk menu item
     * mMainmenuClicked untuk implementasi menu mainmenu ketika diklik
     * mLoginClicked untuk implementasi menu login ketika diklik
     */

    @Override
    public void mMainmenuClicked(ActionEvent evt) {
        try {
            da.initDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(AppImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        ubahPanel("card2", "Simulasi Vending Machine", 640, 480);
    }

    @Override
    public void mLoginClicked(ActionEvent evt) {
        try {
            da.initDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(AppImplements.class.getName()).log(Level.SEVERE, null, ex);
        }
        ubahPanel("login_admin", "Login Admin", 600, 600);
    }
}
