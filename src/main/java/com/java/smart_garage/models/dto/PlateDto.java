package com.java.smart_garage.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PlateDto {

    @NotNull
    @Size(min=7,max = 8,message = "Registration plate must be between 7 or 8 characters.")
    private String plate;

    public PlateDto() {
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
    public String getPlate() {
        return plate;
    }

}
