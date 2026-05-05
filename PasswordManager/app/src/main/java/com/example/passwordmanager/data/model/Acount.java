package com.example.passwordmanager.data.model;

import java.io.Serializable;
public class Acount implements Serializable {
    private String service,  password, paymentInterval, email, category, link, id;
    private long madeOn;
    private boolean isSubscription;
    private double cost;

    public Acount(String service, String password, String paymentInterval, String email, String category, String link, String id, long madeOn, double cost, boolean isSubscription) {
        this.service = service;
        this.password = password;
        this.paymentInterval = paymentInterval;
        this.email = email;
        this.category = category;
        this.link = link;
        this.id = id;
        this.madeOn = madeOn;
        this.isSubscription = isSubscription;
        this.cost = cost;
    }

    public Acount() {
    }

    public String getService() {
        return service;
    }

    public boolean isSubscription() {
        return isSubscription;
    }

    public void setSubscription(boolean subscription) {
        isSubscription = subscription;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}