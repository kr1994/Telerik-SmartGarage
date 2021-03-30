package com.java.smart_garage.models.dto;


import com.java.smart_garage.models.CubicCapacity;
import com.java.smart_garage.models.Fuel;
import com.java.smart_garage.models.HorsePower;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class EngineDto {

    @NotNull
    @Positive(message = "Horse power Id  must be positive.")
    private HorsePowerDto horsePower;

    @NotNull
    @Positive(message = "Fuel Id must be positive.")
    private FuelDto fuel;

    @NotNull
    @Positive(message = "Cubic Capacity Id must be positive.")
    private CubicCapacityDto cubicCapacity;

    public EngineDto() {
    }

    public void setHpw(HorsePowerDto horsePower) {
        this.horsePower = horsePower;
    }

    public void setFuel(FuelDto fuel) {
        this.fuel = fuel;
    }

    public void setCc(CubicCapacityDto cubicCapacity) {
        this.cubicCapacity = cubicCapacity;
    }

    public HorsePowerDto getHorsePower() {
        return horsePower;
    }

    public FuelDto getFuel() {
        return fuel;
    }

    public CubicCapacityDto getCc() {
        return cubicCapacity;
    }
}
