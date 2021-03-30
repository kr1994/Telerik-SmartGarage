package com.java.smart_garage.models.dto;

import com.java.smart_garage.models.Manufacturer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


public class ModelDto {

    @NotNull
    @Size(min=1,max=20, message = "Model name must be between 1 and 20 characters long.")
    private String modelName;

    @NotNull
    @Positive(message = "Manufacturer Id must be positive.")
    private ManufacturerDto manufacturer;

    public ModelDto() {
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setManufacturerId(ManufacturerDto manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModelName() {
        return modelName;
    }

    public ManufacturerDto getManufacturer() {
        return manufacturer;
    }
}
