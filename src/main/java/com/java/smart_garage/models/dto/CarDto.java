package com.java.smart_garage.models.dto;

import com.java.smart_garage.models.*;

public class CarDto {


    private Model model;

    private RegistrationPlate registrationPlate;

    private Identification identifications;

    private Year year;

    private Colour colour;

    private Engine engine;

    public CarDto() {
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setRegistrationPlate(RegistrationPlate registrationPlate) {
        this.registrationPlate = registrationPlate;
    }

    public void setIdentifications(Identification identifications) {
        this.identifications = identifications;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Model getModel() {
        return model;
    }

    public RegistrationPlate getRegistrationPlate() {
        return registrationPlate;
    }

    public Identification getIdentifications() {
        return identifications;
    }

    public Year getYear() {
        return year;
    }

    public Colour getColour() {
        return colour;
    }

    public Engine getEngine() {
        return engine;
    }
}
