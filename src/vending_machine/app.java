/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vending_machine;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author afar0308
 */
public class app {
    
    public static void main(String[] args) {
        // implementasi kelas abstrak login_admin
        login_admin la = new login_admin() {
            
            @Override
            public void bLoginClicked(MouseEvent evt) {
                String user = "cakman";
                String pass = "cakman123";
                //        if(user.equalsIgnoreCase(jTextField1.getText()) && pass.equalsIgnoreCase(jPasswordField1.getText())) {
                //            this.setVisible(false);
                //            new database().setVisible(true);
                //        } else {
                //            javax.swing.JOptionPane.showMessageDialog(null, "Maaf user atau password anda salah");
                //            jTextField1.setText("");
                //            jPasswordField1.setText("");
                //            jTextField1.requestFocus();
                //        }

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
                System.out.println("Anda membatalkan login");
            }
        };
        
        // implementasi kelas abstrak App
        App a = new App() {
            @Override
            public void tambahPanel() {
                getPanel().add(la, "login_admin");
            }
            
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
    
            @Override
            public void bKeluarClicked(ActionEvent evt) {
                System.exit(0);
            }
            
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
        };
    }
}
