package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "engines")
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "engine_id")
    private int engineId;


    @Column(name = "hpw")
    private int hpw;

    @ManyToOne
    @JoinColumn(name = "fuel_id")
    private Fuel fuel;


    @Column(name = "cubic_capacity")
    private int cc;

    public Engine() {
    }

    public Engine(int engineId, int hpw, Fuel fuel, int cc) {
        this.engineId = engineId;
        this.hpw = hpw;
        this.fuel = fuel;
        this.cc = cc;
    }

    public int getEngineId() {
        return engineId;
    }

    public void setEngineId(int engineId) {
        this.engineId = engineId;
    }

    public int getHpw() {
        return hpw;
    }

    public void setHpw(int hpw) {
        this.hpw = hpw;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public int getCubicCapacity() {
        return cc;
    }

    public void setCubicCapacity(int cc) {
        this.cc = cc;
    }
}

