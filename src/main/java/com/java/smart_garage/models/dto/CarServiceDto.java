package com.java.smart_garage.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CarServiceDto {

    @NotNull
    @Positive(message = "Car Id must be positive.")
    private int carId;

    @NotNull
    @Positive(message = "Service Id must be positive.")
    private int serviceId;

    public CarServiceDto() {
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getCarId() {
        return carId;
    }

    public int getServiceId() {
        return serviceId;
    }
}
