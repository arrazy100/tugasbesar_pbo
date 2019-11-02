/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vending_machine;

import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author afar0308
 */
public class app {
    
    public static void main(String[] args) throws SQLException {
        
        AppImplements main_app = new AppImplements();
        main_app.ubahJudul("Simulasi Vending Machine");
        
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
                        emptyField();
                        System.out.println("login berhasil");
                        main_app.ubahPanel("database_admin", "Database Admin", 725, 621);
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
                main_app.ubahPanel("card2", "Simulasi Vending Machine", 640, 480);
            }
        };
        
        admin da = new admin();
        panel_mesin ms = new panel_mesin(0);
        panel_mesin mm = new panel_mesin(1);
        
        main_app.tambahPanel(la, "login_admin");
        main_app.tambahPanel(da, "database_admin");
        main_app.tambahPanel(ms, "mesin_snack");
        main_app.tambahPanel(mm, "mesin_minuman");
    }
}
