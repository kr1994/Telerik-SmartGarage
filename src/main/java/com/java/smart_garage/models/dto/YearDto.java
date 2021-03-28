package com.java.smart_garage.models.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class YearDto {

    @NotNull
    @Min(value = 1950,message = "The car year can not be earlier than 1950.")
    private int year;

    public YearDto() {
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }
}
