package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Cars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private int carId;

    @ManyToOne
    @Column(name = "manufacturer_id")
    private Manufacturers manufacturer;

    @OneToOne
    @Column(name = "plate_id")
    private RegistrationPlates registrationPlate;

    @OneToOne
    @Column(name = "identification_id")
    private Identifications identifications;

    @ManyToOne
    @Column(name = "year_id")
    private Years year;

    @ManyToOne
    @Column(name = "colour_id")
    private Colours colour;

    @ManyToOne
    @Column(name = "engine_id")
    private Engines engine;

    public Cars() {
    }

    public Cars(int carId,
                Manufacturers manufacturer,
                RegistrationPlates registrationPlate,
                Identifications identifications,
                Years year, Colours colour,
                Engines engine) {
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

    public void setManufacturer(Manufacturers manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setRegistrationPlate(RegistrationPlates registrationPlate) {
        this.registrationPlate = registrationPlate;
    }

    public void setIdentifications(Identifications identifications) {
        this.identifications = identifications;
    }

    public void setYear(Years year) {
        this.year = year;
    }

    public void setColour(Colours colour) {
        this.colour = colour;
    }

    public void setEngine(Engines engine) {
        this.engine = engine;
    }

    public int getCarId() {
        return carId;
    }


    public Manufacturers getManufacturer() {
        return manufacturer;
    }


    public RegistrationPlates getRegistrationPlate() {
        return registrationPlate;
    }

    public Identifications getIdentifications() {
        return identifications;
    }

    public Years getYear() {
        return year;
    }

    public Colours getColour() {
        return colour;
    }

    public Engines getEngine() {
        return engine;
    }

}
