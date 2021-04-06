package com.java.smart_garage.models.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class EngineDto {

    @NotNull
    @Positive(message = "Horse power Id  must be positive.")
    private int horsePower;

    @NotNull
    @Positive(message = "Cubic Capacity Id must be positive.")
    private int cc;

    @NotNull
    @Positive(message = "Fuel Id must be positive.")
    private int fuel;



    public EngineDto() {
    }

    public void setHpw(int horsePower) {
        this.horsePower = horsePower;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public void setCc(int cubicCapacity) {
        this.cc = cubicCapacity;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public int getFuel() {
        return fuel;
    }

    public int getCc() {
        return cc;
    }
}
