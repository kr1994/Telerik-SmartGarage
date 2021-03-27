package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "engines")
public class Engines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "engine_id")
    private int engineId;

    @ManyToOne
    @JoinColumn(name = "hpw_id")
    private HorsePowers hpw;

    @ManyToOne
    @JoinColumn(name = "fuel_id")
    private Fuels fuel;

    @ManyToOne
    @JoinColumn(name = "cubic_capacity_id")
    private CubicCapacities cc;

    public Engines() {
    }

    public Engines(int engineId, HorsePowers hpw, Fuels fuel, CubicCapacities cc) {
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

    public HorsePowers getHpw() {
        return hpw;
    }

    public void setHpw(HorsePowers hpw) {
        this.hpw = hpw;
    }

    public Fuels getFuel() {
        return fuel;
    }

    public void setFuel(Fuels fuel) {
        this.fuel = fuel;
    }

    public CubicCapacities getCubicCapacity() {
        return cc;
    }

    public void setCubicCapacity(CubicCapacities cc) {
        this.cc = cc;
    }
}

