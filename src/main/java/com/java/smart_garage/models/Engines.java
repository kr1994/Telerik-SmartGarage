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

    @Column(name = "fuel_id")
    private int fuelId;

    @ManyToOne
    @JoinColumn(name = "cubic_capacity_id")
    private CubicCapacities cc;

    public Engines() {
    }

    public Engines(int engineId, HorsePowers hpw, int fuelId, CubicCapacities cc) {
        this.engineId = engineId;
        this.hpw = hpw;
        this.fuelId = fuelId;
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

    public int getFuelId() {
        return fuelId;
    }

    public void setFuelId(int fuelId) {
        this.fuelId = fuelId;
    }

    public CubicCapacities getCubicCapacity() {
        return cc;
    }

    public void setCubicCapacity(CubicCapacities cc) {
        this.cc = cc;
    }
}

