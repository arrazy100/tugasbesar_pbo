/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vending_machine;

/**
 *
 * @author afar0308
 */
public class mesin_minuman extends mesin_makanan {
    
    public mesin_minuman() {
        dataBarang = data_makanan();
    }
    
    @Override
    public String data_makanan() {
        String sql = "SELECT kode_barang, nama_barang, gambar, harga_barang FROM stok "
                    + "WHERE kode_barang LIKE 'D-%'";
        
        return sql;
    }
}
