package com.example.tudtc_app_shop_manager.model;

import java.util.Date;

public class HoaDon {
    private int id;
    private Date datePurchase;
    private String username;
    private boolean paid;
    private int status;

    public HoaDon() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(Date datePurchase) {
        this.datePurchase = datePurchase;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HoaDon{" +
                "id=" + id +
                ", datePurchase=" + datePurchase +
                ", username='" + username + '\'' +
                ", paid=" + paid +
                ", status=" + status +
                '}';
    }

    public HoaDon(int id, Date datePurchase, String username, boolean paid, int status) {
        this.id = id;
        this.datePurchase = datePurchase;
        this.username = username;
        this.paid = paid;
        this.status = status;
    }
}
