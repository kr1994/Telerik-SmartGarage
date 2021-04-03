package com.java.smart_garage.models.dto;

import com.java.smart_garage.models.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CarDto {

    @NotNull
    @Positive(message = "Model Id must be positive.")
    private int modelId;

    @NotNull
    private String plate;

    @NotNull
    private String identification;

    @NotNull
    @Positive(message = "Year Id must be positive.")
    private int year;

    @NotNull
    @Positive(message = "Colour Id must be positive.")
    private int colourId;

    @NotNull
    @Positive(message = "Engine Id must be positive.")
    private int engineId;

    @NotNull
    @Positive(message = "Customer Id must be positive.")
    private int customerId;

    public CarDto() {
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setColourId(int colourId) {
        this.colourId = colourId;
    }

    public void setEngineId(int engineId) {
        this.engineId = engineId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getModelId() {
        return modelId;
    }

    public String getPlate() {
        return plate;
    }

    public String getIdentification() {
        return identification;
    }

    public int getYear() {
        return year;
    }

    public int getColourId() {
        return colourId;
    }

    public int getEngineId() {
        return engineId;
    }

    public int getCustomerId() {
        return customerId;
    }
}