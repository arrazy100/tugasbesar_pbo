/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vending_machine;

import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author afar0308
 */
public class admin extends javax.swing.JPanel {

    /**
     * Creates new form database_admin
     * @throws java.sql.SQLException
     */
    public admin() throws SQLException {
        initComponents();
        initButton();
        initDatabase();
        initPanelBarang();
    }
    
    private Connection connect() {
        // connect to database
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:databases/stok.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    private void initButton() {
        btn_restok = new ImageIcon(getClass().getResource("images/button_restok.png"));
        btn_restok_entered = new ImageIcon(getClass().getResource("images/button_restok_entered.png"));
    }
    
    public void initDatabase() throws SQLException {
        Connection conn = connect();
        
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM stok");
        jTable1.setModel(buildTableModel(rs));
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.setRowSelectionAllowed(false);
        jTable1.setRowHeight(52);
        
        ResultSet rs_m = stmt.executeQuery("SELECT nama_barang, gambar, MAX(pembelian) "
                                + "FROM STOK WHERE kode_barang LIKE 'D-%'");
        minumanTerlaku.setText(rs.getString("nama_barang"));
        ImageIcon dc = new ImageIcon("images/" + rs_m.getObject(2));
        dc.getImage().flush();
        drink_icon.setIcon(dc);
        drink_icon.setText("a");
        
        ResultSet rs_s = stmt.executeQuery("SELECT nama_barang, gambar, MAX(pembelian) "
                                + "FROM STOK WHERE kode_barang LIKE 'S-%'");
        makananTerlaku.setText(rs.getString("nama_barang"));
        ImageIcon sc = new ImageIcon("images/" + rs_s.getObject(2));
        sc.getImage().flush();
        snack_icon.setIcon(sc);
        snack_icon.setText("a");
        
        conn.close();
    }
    
    private void initPanelBarang() {
        panel_barang = new javax.swing.JPanel();
        panel_barang.setLayout(new BoxLayout(panel_barang, BoxLayout.PAGE_AXIS));
        panel_barang.setAlignmentX(Component.CENTER_ALIGNMENT);
        iKategori_barang = new JComboBox<>();
        iKategori_barang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Snack", "Minuman" }));
        iKode_barang = new javax.swing.JTextField();
        iKode_barang.setPreferredSize(new Dimension(200, 20));
        iNama_barang = new javax.swing.JTextField();
        iHarga_barang = new javax.swing.JTextField();
        iJumlah_barang = new javax.swing.JTextField();
        
        iGambar = new javax.swing.JFileChooser();
        iGambar.setCurrentDirectory(new File(System.getProperty("user.dir")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files",
                    "jpg", "gif", "png");
        iGambar.setFileFilter(filter);
        
        iBtn_gambar = new javax.swing.JButton("Pilih gambar");
        iBtn_gambar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bGambarClicked(evt);
            }
        });
        
        gambar_baru = null;
        lFile = new javax.swing.JLabel("");
        lFile.setPreferredSize(new Dimension(200, 20));
        
        panel_barang.add(new javax.swing.JLabel("Kategori: "));
        panel_barang.add(iKategori_barang);
        panel_barang.add(new javax.swing.JLabel("Kode barang: "));
        panel_barang.add(iKode_barang);
        panel_barang.add(new javax.swing.JLabel("Nama barang: "));
        panel_barang.add(iNama_barang);
        panel_barang.add(new javax.swing.JLabel("Harga barang: "));
        panel_barang.add(iHarga_barang);
        panel_barang.add(new javax.swing.JLabel("Jumlah barang: "));
        panel_barang.add(iJumlah_barang);
        panel_barang.add(new javax.swing.JLabel("Gambar barang: "));
        panel_barang.add(iBtn_gambar);
        panel_barang.add(lFile);
    }
    
    private DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            if (column == columnCount) {
                columnNames.add("aksi");
                break;
            }
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                if (columnIndex == columnCount) {
                    vector.add(btn_restok);
                    break;
                }
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == getColumnCount()-1) return ImageIcon.class;
                else return Object.class;
            }
        };
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        minumanTerlaku = new javax.swing.JLabel();
        makananTerlaku = new javax.swing.JLabel();
        drink_icon = new javax.swing.JLabel();
        snack_icon = new javax.swing.JLabel();
        bTambahbarang = new javax.swing.JButton();
        bEditbarang = new javax.swing.JButton();
        bHapusbarang = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(725, 600));
        setPreferredSize(new java.awt.Dimension(725, 600));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "kode", "nama", "harga", "jumlah_barang", "pembelian", "aksi"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Long.class, java.lang.Long.class, java.lang.Long.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTable1MouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setText("STOK BARANG");

        jLabel2.setText("Minuman yang paling banyak dibeli:");

        jLabel3.setText("Makanan yang paling banyak dibeli:");

        minumanTerlaku.setText("jLabel4");

        makananTerlaku.setText("jLabel5");

        drink_icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        drink_icon.setText("Minuman");
        drink_icon.setMinimumSize(new java.awt.Dimension(120, 120));
        drink_icon.setPreferredSize(new java.awt.Dimension(120, 120));

        snack_icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        snack_icon.setText("Makanan");
        snack_icon.setMinimumSize(new java.awt.Dimension(120, 120));
        snack_icon.setPreferredSize(new java.awt.Dimension(120, 120));

        bTambahbarang.setText("Tambah");
        bTambahbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahbarangActionPerformed(evt);
            }
        });

        bEditbarang.setText("Edit");
        bEditbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bEditbarangActionPerformed(evt);
            }
        });

        bHapusbarang.setText("Hapus");
        bHapusbarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusbarangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(drink_icon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(snack_icon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(minumanTerlaku))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(makananTerlaku)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 701, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(273, 273, 273))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bTambahbarang)
                .addGap(18, 18, 18)
                .addComponent(bEditbarang)
                .addGap(18, 18, 18)
                .addComponent(bHapusbarang)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(drink_icon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(minumanTerlaku))
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(snack_icon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(makananTerlaku))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bTambahbarang)
                            .addComponent(bEditbarang)
                            .addComponent(bHapusbarang))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        mouseClick(evt);
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MousePressed
        // TODO add your handling code here:
        eventMouse(btn_restok_entered, evt);
    }//GEN-LAST:event_jTable1MousePressed

    private void jTable1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseReleased
        // TODO add your handling code here:
        eventMouse(btn_restok, evt);
    }//GEN-LAST:event_jTable1MouseReleased

    private void bEditbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bEditbarangActionPerformed
        try {
            // TODO add your handling code here:
            edit_barang();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bEditbarangActionPerformed

    private void bTambahbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahbarangActionPerformed
        try {
            // TODO add your handling code here:
            tambah_barang();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bTambahbarangActionPerformed

    private void bHapusbarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusbarangActionPerformed
        try {
            // TODO add your handling code here:
            hapus_barang();
        } catch (IOException | SQLException ex) {
            Logger.getLogger(admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bHapusbarangActionPerformed
    
    private void bGambarClicked(java.awt.event.ActionEvent evt) {
        int inp = iGambar.showOpenDialog(null);
        if (inp == javax.swing.JFileChooser.APPROVE_OPTION) {
            gambar_baru = iGambar.getSelectedFile();
            lFile.setText(gambar_baru.toString());
        }
    }
    
    private void mouseClick(java.awt.event.MouseEvent evt) {
        int row = jTable1.rowAtPoint(evt.getPoint());
        int col = jTable1.columnAtPoint(evt.getPoint());
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        String s = model.getValueAt(row, 0).toString();
        
        if (col == 0) { // jika kode_barang diklik oleh mouse
            if ("D".equals(s.substring(0, 1))) iKategori_barang.setSelectedIndex(1);
            else if ("S".equals(s.substring(0, 1))) iKategori_barang.setSelectedIndex(0);
            iKode_barang.setText(s.substring(2));
            iNama_barang.setText(model.getValueAt(row, 1).toString());
            iHarga_barang.setText(model.getValueAt(row, 2).toString());
            iJumlah_barang.setText(model.getValueAt(row, 3).toString());
            iGambar.setCurrentDirectory(new File(System.getProperty("user.dir") + "/images"));
            
            String curDir = System.getProperty("user.dir") + "/images";
            if (iKategori_barang.getSelectedIndex() == 0) {
                iGambar.setSelectedFile(new File(curDir + "/S-" + iKode_barang.getText() + ".png"));
            } else {
                iGambar.setSelectedFile(new File(curDir + "/D-" + iKode_barang.getText() + ".png"));
            }
            lFile.setText(iGambar.getSelectedFile() + "");
            System.out.println(lFile.getText());
        } else if (col == 5) { //jika button restok diklik oleh mouse
            tambah_stok(s);
            try {
                initDatabase();
            } catch (SQLException ex) {
                Logger.getLogger(admin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void eventMouse(ImageIcon ic, java.awt.event.MouseEvent evt) {
        int row = jTable1.rowAtPoint(evt.getPoint());
        int col = jTable1.columnAtPoint(evt.getPoint());
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        if (col == 5) model.setValueAt(ic, row, model.getColumnCount()-1);
    }
    
    private void salinGambar(File src, File dest) throws IOException {
        try {
            Files.copy(src.toPath(), dest.toPath());
        }
        catch (IOException e) {
            Files.delete(dest.toPath());
            Files.copy(src.toPath(), dest.toPath());
        }
    }
    
    private void kosongkanField() {
        iKategori_barang.setSelectedIndex(0);
        iKode_barang.setText("");
        iNama_barang.setText("");
        iHarga_barang.setText("");
        iJumlah_barang.setText("");
        iGambar.setSelectedFile(null);
        lFile.setText("");
    }
    
    private boolean fieldTerisi() {
        return (!"".equals(iKode_barang.getText()) && !"".equals(iNama_barang.getText())
                && !"".equals(iHarga_barang.getText()) && !"".equals(iJumlah_barang.getText())
                && !"".equals(lFile.getText()));
    }
    
    private void tambah_barang() throws SQLException, IOException {
        kosongkanField();
        
        int inp = javax.swing.JOptionPane.showConfirmDialog(null, panel_barang, "Tambah Barang: ", 
                                                javax.swing.JOptionPane.OK_CANCEL_OPTION,
                                                javax.swing.JOptionPane.PLAIN_MESSAGE);
        
        if (inp == javax.swing.JOptionPane.OK_OPTION && fieldTerisi()) {
            String sql = "INSERT INTO stok(kode_barang, nama_barang, harga_barang, "
                    + "jumlah_barang, pembelian, gambar) VALUES ("
                    + "?, ?, ?, ?, 0, ?)";
            
            int kategori = iKategori_barang.getSelectedIndex();

            try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                if (kategori == 0) {
                    pstmt.setString(1, "S-" + iKode_barang.getText());
                    pstmt.setString(5, "S-" + iKode_barang.getText() + ".png");
                    File baru = new File("images/S-" + iKode_barang.getText() + ".png");
                    
                    salinGambar(gambar_baru, baru);
                }
                else if (kategori == 1) {
                    pstmt.setString(1, "D-" + iKode_barang.getText());
                    pstmt.setString(5, "D-" + iKode_barang.getText() + ".png");
                    File baru = new File("images/D-" + iKode_barang.getText() + ".png");
                    
                    salinGambar(gambar_baru, baru);
                }
                
                pstmt.setString(2, iNama_barang.getText());
                
                try {
                    pstmt.setLong(3, Long.parseLong(iHarga_barang.getText()));
                    pstmt.setLong(4, Long.parseLong(iJumlah_barang.getText()));
                    
                } catch (NumberFormatException e) {
                    javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
                    pstmt.setLong(3, 0);
                    pstmt.setLong(4, 0);
                }
                
                pstmt.executeUpdate();
                conn.close();
            } catch (SQLException e) {
                String pesan = e.getMessage();
                if (pesan.contains("UNIQUE constraint failed")) {
                    pesan = "kode_barang sudah ada";
                }
                javax.swing.JOptionPane.showMessageDialog(null, pesan);
            }

            initDatabase();
   
        } else if (inp == javax.swing.JOptionPane.OK_OPTION && !fieldTerisi()) {
            javax.swing.JOptionPane.showMessageDialog(null, "Field tidak boleh ada yang kosong!");
        }
        kosongkanField();
    }
    
    private void edit_barang() throws IOException, SQLException {
        if ("".equals(iKode_barang.getText())) {
            javax.swing.JOptionPane.showMessageDialog(null, "Klik kode_barang pada baris yang ingin diedit terlebih dulu");
        } else {
            int inp = javax.swing.JOptionPane.showConfirmDialog(null, panel_barang, "Edit Barang", 
                                                javax.swing.JOptionPane.OK_CANCEL_OPTION,
                                                javax.swing.JOptionPane.PLAIN_MESSAGE);
            
            String kodeTemp = iKode_barang.getText();
            
            if (inp == javax.swing.JOptionPane.OK_OPTION && fieldTerisi()) {
                String sql = "UPDATE stok SET kode_barang = ?, nama_barang = ?, harga_barang = ?,"
                        + "jumlah_barang = ?, gambar = ? WHERE kode_barang = ?";

                int kategori = iKategori_barang.getSelectedIndex();

                try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    if (kategori == 0) {
                        pstmt.setString(1, "S-" + iKode_barang.getText());
                        pstmt.setString(6, "S-" + kodeTemp);
                        pstmt.setString(5, "S-" + iKode_barang.getText() + ".png");
                        File baru = new File("images/S-" + iKode_barang.getText() + ".png");

                        salinGambar(gambar_baru, baru);
                    } else if (kategori == 1) {
                        pstmt.setString(1, "D-" + iKode_barang.getText());
                        pstmt.setString(6, "D-" + kodeTemp);
                        pstmt.setString(5, "D-" + iKode_barang.getText() + ".png");
                        File baru = new File("images/D-" + iKode_barang.getText() + ".png");

                        salinGambar(gambar_baru, baru);
                    }

                    pstmt.setString(2, iNama_barang.getText());

                    try {
                        pstmt.setLong(3, Long.parseLong(iHarga_barang.getText()));
                        pstmt.setLong(4, Long.parseLong(iJumlah_barang.getText()));

                    } catch (NumberFormatException e) {
                        javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
                        pstmt.setLong(3, 0);
                        pstmt.setLong(4, 0);
                    }

                    pstmt.executeUpdate();
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

                initDatabase();
            
            } else if (inp == javax.swing.JOptionPane.OK_OPTION && !fieldTerisi()) {
                javax.swing.JOptionPane.showMessageDialog(null, "Field tidak boleh ada yang kosong!");
            }
        }
        
        kosongkanField();
    }
    
    private void hapus_barang() throws IOException, SQLException {
        String sql = "DELETE FROM stok WHERE kode_barang = ?";
        
        if ("".equals(iKode_barang.getText())) {
            javax.swing.JOptionPane.showMessageDialog(null, "Klik kode_barang pada baris yang ingin dihapus terlebih dulu");
        } else {
            int inp = javax.swing.JOptionPane.showConfirmDialog(null, null, "Yakin ingin hapus "
                    + iNama_barang.getText(),
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.PLAIN_MESSAGE);
            if (inp == javax.swing.JOptionPane.YES_OPTION) {
                try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, iKode_barang.getText());
                    Files.delete( (iGambar.getSelectedFile() ).toPath());
                    pstmt.executeUpdate();
                    conn.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                
                initDatabase();
            }
        }
        
        kosongkanField();
    }
    
    private void tambah_stok(String s) {
        String sql = "UPDATE stok SET jumlah_barang = jumlah_barang + ? WHERE kode_barang = ?";
        String stok = javax.swing.JOptionPane.showInputDialog("Jumlah barang yang ingin ditambah:");
        
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            try {
                pstmt.setLong(1, Long.parseLong(stok));
            } catch (NumberFormatException e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Hanya menerima input angka");
                pstmt.setLong(1, 0);
            }
            pstmt.setString(2, s);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bEditbarang;
    private javax.swing.JButton bHapusbarang;
    private javax.swing.JButton bTambahbarang;
    private javax.swing.JLabel drink_icon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel makananTerlaku;
    private javax.swing.JLabel minumanTerlaku;
    private javax.swing.JLabel snack_icon;
    // End of variables declaration//GEN-END:variables
    private ImageIcon btn_restok;
    private ImageIcon btn_restok_entered;
    private javax.swing.JPanel panel_barang;
    private javax.swing.JComboBox<String> iKategori_barang;
    private javax.swing.JTextField iKode_barang;
    private javax.swing.JTextField iNama_barang;
    private javax.swing.JTextField iHarga_barang;
    private javax.swing.JTextField iJumlah_barang;
    private javax.swing.JFileChooser iGambar;
    private javax.swing.JButton iBtn_gambar;
    private javax.swing.JLabel lFile;
    private File gambar_baru;
}
