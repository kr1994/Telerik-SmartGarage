package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "engines")
public class Engines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "engine_id")
    private int engineId;

    @Column(name = "hpw_id")
    private int hpwId;

    @Column(name = "fuel_id")
    private int fuelId;

    @OneToOne
    @JoinColumn(name = "cubic_capacity_id")
    private CubicCapacities cc;

    public Engines() {
    }

    public Engines(int engineId, int hpwId, int fuelId, CubicCapacities cc) {
        this.engineId = engineId;
        this.hpwId = hpwId;
        this.fuelId = fuelId;
        this.cc = cc;
    }

    public int getEngineId() {
        return engineId;
    }

    public void setEngineId(int engineId) {
        this.engineId = engineId;
    }

    public int getHpwId() {
        return hpwId;
    }

    public void setHpwId(int hpwId) {
        this.hpwId = hpwId;
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

