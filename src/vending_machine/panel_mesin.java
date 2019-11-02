/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vending_machine;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author afar0308
 */
public class panel_mesin extends javax.swing.JPanel {
    
    /**
     * Creates new form panel_mesin
     */
    
    public panel_mesin(int kategori) {
        initComponents();
        try {
            initMesin(kategori);
        } catch (SQLException ex) {
            Logger.getLogger(panel_mesin.class.getName()).log(Level.SEVERE, null, ex);
        }
        initButton(kategori);
    }
    
    public void initMesin(int kategori) throws SQLException {
        if (kategori == 0) {
            mM = new mesin_makanan();
        } else {
            mM = new mesin_minuman();
        }
        Connection conn = mM.connect();
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(mM.dataBarang);
        
        Vector<ImageIcon> barang = new Vector<ImageIcon>();
        Vector<javax.swing.JLabel> kode_barang = new Vector<javax.swing.JLabel>();
        Vector<javax.swing.JButton> nama_barang = new Vector<javax.swing.JButton>();
        Vector<String> harga_barang = new Vector<String>();
        
        while (rs.next()) {
            kode_barang.add(new javax.swing.JLabel(rs.getObject(1) + ""));
            nama_barang.add(new javax.swing.JButton(rs.getObject(2) + ""));
            barang.add(new ImageIcon("images/" + rs.getObject(3)));
            harga_barang.add(rs.getObject(4) + "");
        }
        
        int height = (barang.size() / 2 + 1) * 160;
        panelBarang.setPreferredSize(new Dimension(panelBarang.getWidth(), height));
        panelBarang.removeAll();

        for (int i=0; i<barang.size(); i++) {
            javax.swing.JLabel snack = kode_barang.elementAt(i);
            snack.setIcon(barang.elementAt(i));
            snack.setBounds((i%2)*130, (i/2)*160, 120, 120);
            
            javax.swing.JButton btn = nama_barang.elementAt(i);
            btn.setBounds((i%2)*130, (i/2)*160+130, 120, 20);
            long harga = Long.parseLong(harga_barang.elementAt(i));
            
            btn.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    if (mM.getHarga() == 0) {
                        mM.setBarang(snack.getText(), snack, harga);
                        hrg.setText(harga + "");
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(null, "Hanya bisa memilih 1 barang, klik tombol reset untuk mengulang pilihan!");
                    }
                }
            });
            panelBarang.add(snack);
            panelBarang.add(btn);
        }
        
        conn.close();
    }
    
    private void initButton(int kategori) {
        lKeluaran.setIcon(null);
        
        bOutput.addActionListener(new java.awt.event.ActionListener() {
            
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (mM.getHarga() == 0 && "0".equals(lKembalian.getText())) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Pilih barang terlebih dulu!");
                } else {
                    if (mM.uang_dimasukkan() < mM.getHarga()) {
                        javax.swing.JOptionPane.showMessageDialog(null, "Uang masih kurang");
                    } else if(lKeluaran.getIcon() == null) {
                        lKembalian.setText(mM.kembalian() + "");
                        lKeluaran.setIcon(mM.keluarkan());
                        mM.tambahPembelian();
                        try {
                            initMesin(kategori);
                        } catch (SQLException ex) {
                            Logger.getLogger(panel_mesin.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(null, "Barang sudah dikeluarkan, klik tombol reset untuk memilih barang lagi!");
                    }
                }
            }
        });
        
        bReset.addActionListener(new java.awt.event.ActionListener() {
            
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mM.reset();
                curUang.setText("0");
                hrg.setText("0");
                lKembalian.setText("0");
                lKeluaran.setIcon(null);
            }
        });
        
        b500.addActionListener(new java.awt.event.ActionListener() {
            
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (mM.getHarga() != 0) {
                    mM.masukkan_uang(500);
                    curUang.setText(mM.uang_dimasukkan() + "");
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Pilih barang yang ingin dibeli terlebih dahulu!");
                }
            }
        });
        
        b1000.addActionListener(new java.awt.event.ActionListener() {
            
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (mM.getHarga() != 0) {
                    mM.masukkan_uang(1000);
                    curUang.setText(mM.uang_dimasukkan() + "");
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Pilih barang yang ingin dibeli terlebih dahulu!");
                }
            }
        });
        
        b1500.addActionListener(new java.awt.event.ActionListener() {
            
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (mM.getHarga() != 0) {
                    mM.masukkan_uang(1500);
                    curUang.setText(mM.uang_dimasukkan() + "");
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Pilih barang yang ingin dibeli terlebih dahulu!");
                }
            }
        });
        
        b2000.addActionListener(new java.awt.event.ActionListener() {
            
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (mM.getHarga() != 0) {
                    mM.masukkan_uang(2000);
                    curUang.setText(mM.uang_dimasukkan() + "");
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Pilih barang yang ingin dibeli terlebih dahulu!");
                }
            }
        });
        
        b2500.addActionListener(new java.awt.event.ActionListener() {
            
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (mM.getHarga() != 0) {
                    mM.masukkan_uang(2500);
                    curUang.setText(mM.uang_dimasukkan() + "");
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Pilih barang yang ingin dibeli terlebih dahulu!");
                }
            }
        });
        
        b3000.addActionListener(new java.awt.event.ActionListener() {
            
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (mM.getHarga() != 0) {
                    mM.masukkan_uang(3000);
                    curUang.setText(mM.uang_dimasukkan() + "");
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Pilih barang yang ingin dibeli terlebih dahulu!");
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane1 = new java.awt.ScrollPane();
        panelBarang = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        b500 = new javax.swing.JButton();
        b1000 = new javax.swing.JButton();
        b1500 = new javax.swing.JButton();
        b2000 = new javax.swing.JButton();
        b2500 = new javax.swing.JButton();
        b3000 = new javax.swing.JButton();
        hrg = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        curUang = new javax.swing.JLabel();
        bReset = new javax.swing.JButton();
        bOutput = new javax.swing.JButton();
        lKeluaran = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lKembalian = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(400, 300));

        javax.swing.GroupLayout panelBarangLayout = new javax.swing.GroupLayout(panelBarang);
        panelBarang.setLayout(panelBarangLayout);
        panelBarangLayout.setHorizontalGroup(
            panelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 280, Short.MAX_VALUE)
        );
        panelBarangLayout.setVerticalGroup(
            panelBarangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 448, Short.MAX_VALUE)
        );

        scrollPane1.add(panelBarang);

        jLabel2.setText("Harga: Rp.");

        b500.setText("500");

        b1000.setText("1000");

        b1500.setText("1500");

        b2000.setText("2000");

        b2500.setText("2500");

        b3000.setText("3000");

        hrg.setText("0");

        jLabel3.setText("Dimasukkan: Rp.");

        curUang.setText("0");

        bReset.setText("Reset");

        bOutput.setText("Keluarkan");

        lKeluaran.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lKeluaran.setMinimumSize(new java.awt.Dimension(280, 120));
        lKeluaran.setPreferredSize(new java.awt.Dimension(280, 120));

        jLabel1.setText("Kembalian: Rp.");

        lKembalian.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lKeluaran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(b500, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(b2000, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(b1000)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(b1500)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(bReset)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(b2500)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(b3000)))
                                .addContainerGap(25, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hrg))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(bOutput)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(curUang))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lKembalian)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(hrg))
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(b500)
                            .addComponent(b1000)
                            .addComponent(b1500))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(b2000)
                            .addComponent(b2500)
                            .addComponent(b3000))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(curUang))
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bReset)
                            .addComponent(bOutput))
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lKembalian))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lKeluaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton b1000;
    private javax.swing.JButton b1500;
    private javax.swing.JButton b2000;
    private javax.swing.JButton b2500;
    private javax.swing.JButton b3000;
    private javax.swing.JButton b500;
    private javax.swing.JButton bOutput;
    private javax.swing.JButton bReset;
    private javax.swing.JLabel curUang;
    private javax.swing.JLabel hrg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lKeluaran;
    private javax.swing.JLabel lKembalian;
    private javax.swing.JPanel panelBarang;
    private java.awt.ScrollPane scrollPane1;
    // End of variables declaration//GEN-END:variables
    private mesin_makanan mM;
}
