package com.java.smart_garage.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FuelDto {

    @NotNull
    @Size(min = 2 , max = 20, message = "Fuel name must be between 2 and 20 characters.")
    private String fuelName;

    public FuelDto() {
    }

    public void setFuelName(String fuelName) {
        this.fuelName = fuelName;
    }

    public String getFuelName() {
        return fuelName;
    }
}
