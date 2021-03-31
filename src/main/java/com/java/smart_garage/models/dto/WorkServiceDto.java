package com.java.smart_garage.models.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class WorkServiceDto {

    @NotNull
    @Size(min=5,message = "Work Service name must be longer than 5 characters.")
    private String workServiceName;

    @NotNull
    @Positive(message = "Work Service price must be positive.")
    private double workServicePrice;

    public WorkServiceDto() {
    }

    public void setWorkServiceName(String workServiceName) {
        this.workServiceName = workServiceName;
    }

    public void setWorkServicePrice(double workServicePrice) {
        this.workServicePrice = workServicePrice;
    }

    public String getWorkServiceName() {
        return workServiceName;
    }

    public double getWorkServicePrice() {
        return workServicePrice;
    }
}
