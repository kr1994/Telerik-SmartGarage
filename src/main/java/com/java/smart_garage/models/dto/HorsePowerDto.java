package com.java.smart_garage.models.dto;

import javax.validation.constraints.Positive;

public class HorsePowerDto {

    @Positive
    private int power;

    public HorsePowerDto() {
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }
}
