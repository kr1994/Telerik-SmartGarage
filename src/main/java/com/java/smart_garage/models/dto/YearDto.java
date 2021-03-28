package com.java.smart_garage.models.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class YearDto {

    @NotNull
    @Min(value = 1950, message = "The car year can not be earlier than 1950.")
    @Max(value = 2021,message = "The car year can not be later than 2021.")
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
