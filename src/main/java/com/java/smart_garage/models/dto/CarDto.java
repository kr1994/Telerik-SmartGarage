package com.java.smart_garage.models.dto;

import com.java.smart_garage.models.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CarDto {

    @NotNull
    @Positive(message = "Model Id must be positive.")
    private int modelId;

    @NotNull
    @Positive(message = "Plate Id must be positive.")
    private int registrationPlateId;

    @NotNull
    @Positive(message = "Identification Id must be positive.")
    private int identificationsId;

    @NotNull
    @Positive(message = "Year Id must be positive.")
    private int yearId;

    @NotNull
    @Positive(message = "Colour Id must be positive.")
    private int colourId;

    @NotNull
    @Positive(message = "Engine Id must be positive.")
    private int engineId;

    public CarDto() {
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public void setRegistrationPlateId(int registrationPlateId) {
        this.registrationPlateId = registrationPlateId;
    }

    public void setIdentificationsId(int identificationsId) {
        this.identificationsId = identificationsId;
    }

    public void setYearId(int yearId) {
        this.yearId = yearId;
    }

    public void setColourId(int colourId) {
        this.colourId = colourId;
    }

    public void setEngineId(int engineId) {
        this.engineId = engineId;
    }

    public int getModelId() {
        return modelId;
    }

    public int getRegistrationPlateId() {
        return registrationPlateId;
    }

    public int getIdentificationsId() {
        return identificationsId;
    }

    public int getYearId() {
        return yearId;
    }

    public int getColourId() {
        return colourId;
    }

    public int getEngineId() {
        return engineId;
    }
}
