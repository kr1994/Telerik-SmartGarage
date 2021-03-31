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
    @JoinColumn(name = "model_id")
    private Model model;


    @Column(name = "plate")
    private String registrationPlate;


    @Column(name = "identification")
    private String identifications;


    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "colour_id")
    private Colour colour;

    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;

    public Car() {
    }

    public Car(int carId,
               Model model,
               String registrationPlate,
               String identifications,
               int year, Colour colour,
               Engine engine) {
        this.carId = carId;
        this.model = model;
        this.registrationPlate = registrationPlate;
        this.identifications = identifications;
        this.year = year;
        this.colour = colour;
        this.engine = engine;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }


    public void setRegistrationPlate(String registrationPlate) {
        this.registrationPlate = registrationPlate;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setIdentifications(String identifications) {
        this.identifications = identifications;
    }

    public void setYear(int year) {
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

    public String getRegistrationPlate() {
        return registrationPlate;
    }

    public String getIdentifications() {
        return identifications;
    }

    public Model getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public Colour getColour() {
        return colour;
    }

    public Engine getEngine() {
        return engine;
    }

}
