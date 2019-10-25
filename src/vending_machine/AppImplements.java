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
            getKartu().show(getPanel(), "login_admin");
            ubahJudul("Login Admin");
            ubahUkuran(600, 600);
        } else if (getSelectedMesin() == 1) {
            System.out.println("Anda memilih mesin minuman");
        }
    }

    /*
     * Event untuk menu item
     * mMainmenuClicked untuk implementasi menu mainmenu ketika diklik
     * mLoginClicked untuk implementasi menu login ketika diklik
     * mKeluarClicked untuk implementasi menu keluar ketika diklik
     */

    @Override
    public void mMainmenuClicked(ActionEvent evt) {
        getKartu().show(getPanel(), "card2");
        ubahJudul("Simulasi Vending Machine");
        ubahUkuran(640, 480);
    }

    @Override
    public void mLoginClicked(ActionEvent evt) {
        getKartu().show(getPanel(), "login_admin");
        ubahJudul("Login Admin");
        ubahUkuran(600, 600);
    }

    @Override
    public void bKeluarClicked(ActionEvent evt) {
        System.exit(0);
    }
}
