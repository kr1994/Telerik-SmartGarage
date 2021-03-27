package com.java.smart_garage.models.dto;

import com.java.smart_garage.models.Manufacturer;

import javax.validation.constraints.NotBlank;


public class ModelDto {

    @NotBlank
    private String modelName;

    private Manufacturer manufacturer;

    public ModelDto() {
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModelName() {
        return modelName;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }
}
