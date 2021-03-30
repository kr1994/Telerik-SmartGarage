package com.java.smart_garage.models.dto;

import com.java.smart_garage.models.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CarDto {

    @NotNull
    @Positive(message = "Model Id must be positive.")
    private ModelDto model;

    @NotNull
    @Positive(message = "Plate Id must be positive.")
    private PlateDto plate;

    @NotNull
    @Positive(message = "Identification Id must be positive.")
    private IdentificationDto identification;

    @NotNull
    @Positive(message = "Year Id must be positive.")
    private YearDto year;

    @NotNull
    @Positive(message = "Colour Id must be positive.")
    private ColourDto colour;

    @NotNull
    @Positive(message = "Engine Id must be positive.")
    private EngineDto engine;

    public CarDto() {
    }

    public void setModel(ModelDto model) {
        this.model = model;
    }

    public void setPlate(PlateDto plate) {
        this.plate = plate;
    }

    public void setIdentification(IdentificationDto identification) {
        this.identification = identification;
    }

    public void setYear(YearDto year) {
        this.year = year;
    }

    public void setColour(ColourDto colour) {
        this.colour = colour;
    }

    public void setEngine(EngineDto engine) {
        this.engine = engine;
    }

    public ModelDto getModel() {
        return model;
    }

    public PlateDto getPlate() {
        return plate;
    }

    public IdentificationDto getIdentification() {
        return identification;
    }

    public YearDto getYear() {
        return year;
    }

    public ColourDto getColour() {
        return colour;
    }

    public EngineDto getEngine() {
        return engine;
    }
}