package com.example.tudtc_app_shop_manager.model;

public class Product {
    private String id;
    private String idCategory;
    private String name;
    private String description;
    private int price;
    private int amount;
    private byte[] image;

    public Product() {
    }

    public byte[] getImage() {
        return image;
    }

    public Product(String id, String idCategory, String name, String description, int price, int amount, byte[] image) {
        this.id = id;
        this.idCategory = idCategory;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.image = image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
