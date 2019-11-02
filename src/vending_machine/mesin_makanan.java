/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vending_machine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
    public String kodeBarang;
    
    public mesin_makanan() {
        dataBarang = data_makanan();
        kodeBarang = "";
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
                    + "WHERE kode_barang LIKE 'S-%' AND jumlah_barang > 0";
        
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
    
    public void setBarang(String kode, javax.swing.JLabel gambar, long harga) {
        kodeBarang = kode;
        outputBarang = gambar.getIcon();
        hargaBarang = harga;
    }
    
    public void tambahPembelian() {
        String sql = "UPDATE stok SET pembelian = pembelian + 1, jumlah_barang = jumlah_barang - 1 WHERE kode_barang = ?";
        
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, kodeBarang);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void reset() {
        uangMasuk = 0;
        hargaBarang = 0;
        outputBarang = null;
        kodeBarang = "";
    }
}
