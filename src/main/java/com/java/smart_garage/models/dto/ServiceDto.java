package com.java.smart_garage.models.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ServiceDto {

    @NotNull
    @Size(min=5,message = "Service name must be longer than 5 characters.")
    private String serviceName;

    @NotNull
    @Positive(message = "Service price must be positive.")
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
