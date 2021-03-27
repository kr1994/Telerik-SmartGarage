package com.java.smart_garage.models.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class ServiceDto {

    @NotBlank
    private String serviceName;

    @Positive
    private double servicePrice;

    public ServiceDto() {
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServicePrice() {
        return servicePrice;
    }
}
