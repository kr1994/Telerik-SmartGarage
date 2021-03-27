package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "engines")
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "engine_id")
    private int engineId;

    @ManyToOne
    @JoinColumn(name = "hpw_id")
    private HorsePower hpw;

    @ManyToOne
    @JoinColumn(name = "fuel_id")
    private Fuel fuel;

    @ManyToOne
    @JoinColumn(name = "cubic_capacity_id")
    private CubicCapacity cc;

    public Engine() {
    }

    public Engine(int engineId, HorsePower hpw, Fuel fuel, CubicCapacity cc) {
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

    public HorsePower getHpw() {
        return hpw;
    }

    public void setHpw(HorsePower hpw) {
        this.hpw = hpw;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public CubicCapacity getCubicCapacity() {
        return cc;
    }

    public void setCubicCapacity(CubicCapacity cc) {
        this.cc = cc;
    }
}

