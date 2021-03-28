package com.java.smart_garage.models.dto;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ColourDto {

    @NotNull
    @Size(min = 2 , max = 20 , message = "Colour length must be between 2 and 20 characters.")
    private String name;

    public ColourDto() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
