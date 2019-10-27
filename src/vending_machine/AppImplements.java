/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vending_machine;

import java.awt.event.ActionEvent;

/**
 *
 * @author afar0308
 */
public class AppImplements extends App {

    @Override
    public void bOkClicked(ActionEvent evt) {
        if (getSelectedMesin() == 0) {
            System.out.println("Anda memilih mesin makanan");
            ubahPanel("login_admin", "Login Admin", 600, 600);
        } else if (getSelectedMesin() == 1) {
            System.out.println("Anda memilih mesin minuman");
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
        ubahPanel("card2", "Simulasi Vending Machine", 640, 480);
    }

    @Override
    public void mLoginClicked(ActionEvent evt) {
        ubahPanel("login_admin", "Login Admin", 600, 600);
    }
}
