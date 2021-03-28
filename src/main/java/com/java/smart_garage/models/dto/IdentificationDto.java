package com.java.smart_garage.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class IdentificationDto {

    @NotNull
    @Size(min = 17 , max = 17 , message = "Identification must be 17 characters.")
    private String identification;

    public IdentificationDto() {
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getIdentification() {
        return identification;
    }

}
