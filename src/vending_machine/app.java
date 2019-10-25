/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vending_machine;

import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author afar0308
 */
public class app {
    
    public static void main(String[] args) {
        
        AppImplements main_app = new AppImplements();
        
        // implementasi kelas abstrak login_admin
        login_admin la = new login_admin() {
            
            /*
             * Event untuk menu item
             * bLoginClicked untuk implementasi tombol login ketika diklik
             * bBatalClicked untuk implementasi tombol batal ketika diklik
             */
            
            @Override
            public void bLoginClicked(MouseEvent evt) {
                String user = "cakman";
                String pass = "cakman123";

                try { //exception handling
                    if (user.equalsIgnoreCase(getUsername()) && pass.equalsIgnoreCase(getPassword())) {
                        System.out.println("login berhasil");
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(null, "Maaf user atau password anda salah");
                        emptyField();
                    }

                }
                catch(Exception e){ //exception handling
                    JOptionPane.showMessageDialog(this, e.getMessage());
                }
            }
            
            @Override
            public void bBatalClicked(MouseEvent evt) {
                main_app.getKartu().show(main_app.getPanel(), "card2");
                main_app.ubahJudul("Simulasi Vending Machine");
                main_app.ubahUkuran(640, 480);
            }
        };
        
        main_app.tambahPanel(la, "login_admin");
    }
}
