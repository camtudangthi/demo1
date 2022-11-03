package com.example.tudtc_app_shop_manager.model;

import android.app.AlertDialog;

public class ChiTietHoaDon {
    private int hoaDon;
    private Product product;
    private int soLuongMua;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(int hoaDon, Product product, int soLuongMua) {
        this.hoaDon = hoaDon;
        this.product = product;
        this.soLuongMua = soLuongMua;
    }

    public int getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(int hoaDon) {
        this.hoaDon = hoaDon;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
                "hoaDon=" + hoaDon +
                ", product=" + product +
                ", soLuongMua=" + soLuongMua +
                '}';
    }
}
