package com.java.smart_garage.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Automobile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private int id;

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


    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;

    public Automobile() {
    }

    public Automobile(int id,
                      Model model,
                      String registrationPlate,
                      String identifications,
                      int year, Colour colour,
                      Engine engine,
                      User user) {
        this.id = id;
        this.model = model;
        this.registrationPlate = registrationPlate;
        this.identifications = identifications;
        this.year = year;
        this.colour = colour;
        this.engine = engine;
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setOwner(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
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

    public User getOwner() {
        return user;
    }


//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || this.getClass() != o.getClass()) return false;
//        Car car = (Car) o;
//        return this.getIdentifications().equals(car.getIdentifications());
//    }
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || this.getClass() != o.getClass()) return false;
//        Car car = (Car) o;
//        return this.getRegistrationPlate().equals(car.getRegistrationPlate());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(identifications);
//    }


}
