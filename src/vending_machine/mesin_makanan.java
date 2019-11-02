/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vending_machine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.Icon;

/**
 *
 * @author afar0308
 */
public class mesin_makanan extends mesin {
    
    private long uangMasuk = 0;
    private long hargaBarang = 0;
    private Icon outputBarang;
    public String dataBarang;
    
    public mesin_makanan() {
        dataBarang = data_makanan();
    }
    
    public Connection connect() {
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
    
    public String data_makanan() {
        String sql = "SELECT kode_barang, nama_barang, gambar, harga_barang FROM stok "
                    + "WHERE kode_barang LIKE 'S-%'";
        
        return sql;
    }

    @Override
    public void masukkan_uang(int jumlah) {
        this.uangMasuk += jumlah;
    }

    @Override
    public long uang_dimasukkan() {
        return uangMasuk;
    }

    @Override
    public long kembalian() {
        return (uangMasuk - hargaBarang);
    }
    
    public Icon keluarkan() {
        return outputBarang;
    }
    
    public long getHarga() {
        return hargaBarang;
    }
    
    public void setBarang(javax.swing.JLabel gambar, long harga) {
        outputBarang = gambar.getIcon();
        hargaBarang = harga;
    }
    
    public void reset() {
        uangMasuk = 0;
        hargaBarang = 0;
        outputBarang = null;
    }
}
