package com.java.smart_garage.models.dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class WorkServiceDto {

    @NotNull
    @Size(min=5,message = "Service name must be longer than 5 characters.")
    private String serviceName;

    @NotNull
    @Positive(message = "Service price must be positive.")
    private double servicePrice;

    public WorkServiceDto() {
    }

    public void setWorkServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setWorkServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getWorkServiceName() {
        return serviceName;
    }

    public double getWorkServicePrice() {
        return servicePrice;
    }
}
