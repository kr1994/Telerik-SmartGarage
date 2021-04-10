package com.java.smart_garage.models.viewDto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class WorkServiceView {

    private String serviceName;

    private double price;

    @JsonIgnore
    private double multiplier;

    private Date date;

    public WorkServiceView() {
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }
}
