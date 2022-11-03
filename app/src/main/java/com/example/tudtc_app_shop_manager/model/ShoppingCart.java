package com.example.tudtc_app_shop_manager.model;

public class ShoppingCart {
    private String idProduct;
    private String username;
    private int amount;
    private boolean select;

    public ShoppingCart() {
    }

    public String getIdProduct() {
        return idProduct;
    }

    public ShoppingCart(String idProduct, String username, int amount, boolean select) {
        this.idProduct = idProduct;
        this.username = username;
        this.amount = amount;
        this.select = select;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "idProduct='" + idProduct + '\'' +
                ", username='" + username + '\'' +
                ", amount=" + amount +
                ", select=" + select +
                '}';
    }
}
