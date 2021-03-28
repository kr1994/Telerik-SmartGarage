package com.java.smart_garage.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CubicCapacityDto {

    @NotNull
    @Positive(message = "Cubic Capacity must be positive.")
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
