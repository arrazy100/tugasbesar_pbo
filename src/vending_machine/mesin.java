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
public abstract class mesin {
    public abstract void masukkan_uang(int jumlah);
    public abstract long uang_dimasukkan();
    public abstract long kembalian();
}
