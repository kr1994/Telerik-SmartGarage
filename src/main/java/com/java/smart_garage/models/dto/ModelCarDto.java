package com.java.smart_garage.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class ModelCarDto {

    @NotNull
    @Size(min = 1, max = 20, message = "Model name must be between 1 and 20 characters long.")
    private String model;

    @NotNull
    private int manufacturer;

    public ModelCarDto() {
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(int manufacturer) {
        this.manufacturer = manufacturer;
    }
}
