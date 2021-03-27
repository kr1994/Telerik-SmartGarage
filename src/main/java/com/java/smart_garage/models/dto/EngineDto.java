package com.java.smart_garage.models.dto;


import com.java.smart_garage.models.CubicCapacity;
import com.java.smart_garage.models.Fuel;
import com.java.smart_garage.models.HorsePower;

public class EngineDto {

    private HorsePower hpw;

    private Fuel fuel;

    private CubicCapacity cc;

    public EngineDto() {

    }

    public void setHpw(HorsePower hpw) {
        this.hpw = hpw;
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
    }

    public void setCc(CubicCapacity cc) {
        this.cc = cc;
    }

    public HorsePower getHpw() {
        return hpw;
    }

    public Fuel getFuel() {
        return fuel;
    }

    public CubicCapacity getCc() {
        return cc;
    }
}
