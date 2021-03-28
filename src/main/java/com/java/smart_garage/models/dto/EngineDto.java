package com.java.smart_garage.models.dto;


import com.java.smart_garage.models.CubicCapacity;
import com.java.smart_garage.models.Fuel;
import com.java.smart_garage.models.HorsePower;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class EngineDto {

    @NotNull
    @Positive(message = "Horse power Id  must be positive.")
    private int hpwId;

    @NotNull
    @Positive(message = "Fuel Id must be positive.")
    private int fuelId;

    @NotNull
    @Positive(message = "Cubic Capacity Id must be positive.")
    private int ccId;

    public EngineDto() {
    }

    public void setHpwId(int hpwId) {
        this.hpwId = hpwId;
    }

    public void setFuelId(int fuelId) {
        this.fuelId = fuelId;
    }

    public void setCcId(int ccId) {
        this.ccId = ccId;
    }

    public int getHpwId() {
        return hpwId;
    }

    public int getFuelId() {
        return fuelId;
    }

    public int getCcId() {
        return ccId;
    }
}
