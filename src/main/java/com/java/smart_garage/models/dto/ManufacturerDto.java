package com.java.smart_garage.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ManufacturerDto {

    @NotNull
    @Size(min = 2 , max = 20, message = "Manufacturer name must be between 2 and 20 characters.")
    private String manufacturerName;

    public ManufacturerDto() {
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }
    public String getManufacturerName() {
        return manufacturerName;
    }

}
