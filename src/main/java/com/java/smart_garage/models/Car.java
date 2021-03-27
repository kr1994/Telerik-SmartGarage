package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private int carId;

    @ManyToOne
    @Column(name = "model_id")
    private Model model;

    @OneToOne
    @Column(name = "plate_id")
    private RegistrationPlate registrationPlate;

    @OneToOne
    @Column(name = "identification_id")
    private Identification identifications;

    @ManyToOne
    @Column(name = "year_id")
    private Year year;

    @ManyToOne
    @Column(name = "colour_id")
    private Colour colour;

    @ManyToOne
    @Column(name = "engine_id")
    private Engine engine;

    public Car() {
    }

    public Car(int carId,
               Manufacturer manufacturer,
               RegistrationPlate registrationPlate,
               Identification identifications,
               Year year, Colour colour,
               Engine engine) {
        this.carId = carId;
        this.manufacturer = manufacturer;
        this.registrationPlate = registrationPlate;
        this.identifications = identifications;
        this.year = year;
        this.colour = colour;
        this.engine = engine;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
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

    public int getCarId() {
        return carId;
    }


    public Manufacturer getManufacturer() {
        return manufacturer;
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
