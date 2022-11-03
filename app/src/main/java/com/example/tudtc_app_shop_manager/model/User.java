package com.example.tudtc_app_shop_manager.model;

public class User {
    private String userName;
    private String password;
    private String phone;
    private String fullName;
    private byte gender;
    private boolean block;

    public User() {
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public User(String userName, String password, String phone, String fullName, byte gender, boolean block) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.fullName = fullName;
        this.gender = gender;
        this.block = block;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender=" + gender +
                ", block=" + block +
                '}';
    }
}
