package com.java.smart_garage.models;


import javax.persistence.*;

@Entity
@Table(name = "fuels")
public class Fuels {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fuel_id")
    private int fuelId;

    @Column(name = "fuel")
    private int fuelName;

    public Fuels() {
    }

    public Fuels(int fuelId, int fuelName) {
        this.fuelId = fuelId;
        this.fuelName = fuelName;
    }

    public void setFuelId(int fuelId) {
        this.fuelId = fuelId;
    }

    public void setFuelName(int fuelName) {
        this.fuelName = fuelName;
    }

    public int getFuelId() {
        return fuelId;
    }

    public int getFuelName() {
        return fuelName;
    }
}
