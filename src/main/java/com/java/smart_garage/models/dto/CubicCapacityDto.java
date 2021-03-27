package com.java.smart_garage.models.dto;

import javax.validation.constraints.Positive;

public class CubicCapacityDto {

    @Positive
    private int cubicCapacity;

    public CubicCapacityDto() {

    }

    public void setCubicCapacity(int cubicCapacity) {
        this.cubicCapacity = cubicCapacity;
    }

    public int getCubicCapacity() {
        return cubicCapacity;
    }


}
