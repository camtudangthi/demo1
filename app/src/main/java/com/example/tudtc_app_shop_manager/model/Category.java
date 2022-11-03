package com.example.tudtc_app_shop_manager.model;

import java.util.Arrays;

public class Category {
    private String idCategory;
    private String nameCategory;
    private String descriptionCategory;
    private byte[] image;

    public Category() {
    }

    public Category(String idCategory, String nameCategory, String descriptionCategory, byte[] image) {
        this.idCategory = idCategory;
        this.nameCategory = nameCategory;
        this.descriptionCategory = descriptionCategory;
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getDescriptionCategory() {
        return descriptionCategory;
    }

    public void setDescriptionCategory(String descriptionCategory) {
        this.descriptionCategory = descriptionCategory;
    }

    @Override
    public String toString() {
        return idCategory;
    }
}
