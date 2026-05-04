package com.example.passwordmanager.data.model;

import java.io.Serializable;
public class Acount implements Serializable {
    private String service, userName, password, owner, paymentInterval, email, category, link, id;
    private long madeOn;
    private int cost;

    public Acount(String service, String userName, String password, String owner, String paymentInterval, String email, String category, String link, long madeOn, int cost) {
        this.service = service;
        this.userName = userName;
        this.password = password;
        this.owner = owner;
        this.paymentInterval = paymentInterval;
        this.email = email;
        this.category = category;
        this.link = link;
        this.madeOn = madeOn;
        this.cost = cost;
    }

    public Acount() {
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPaymentInterval() {
        return paymentInterval;
    }

    public void setPaymentInterval(String paymentInterval) {
        this.paymentInterval = paymentInterval;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public long getMadeOn() {
        return madeOn;
    }

    public void setMadeOn(long madeOn) {
        this.madeOn = madeOn;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}