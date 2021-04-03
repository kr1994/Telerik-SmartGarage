package com.java.smart_garage.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

import static com.java.smart_garage.models.ModelsConstants.ModelsConstants.EMPLOYEE;

@Entity
@Table(name = "cars")
public class Car {

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
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Car() {
    }

    public Car(int id,
               Model model,
               String registrationPlate,
               String identifications,
               int year, Colour colour,
               Engine engine,
               Customer customer) {
        this.id = id;
        this.model = model;
        this.registrationPlate = registrationPlate;
        this.identifications = identifications;
        this.year = year;
        this.colour = colour;
        this.engine = engine;
        this.customer = customer;
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

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return this.getIdentifications().equals(car.getIdentifications());
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifications);
    }


}
