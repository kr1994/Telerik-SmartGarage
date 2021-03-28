package com.java.smart_garage.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CustomerDto {

    @NotNull
    @Positive(message = "User Id must be positive.")
    private int userId;

    @NotNull
    @Size(min = 10,max = 10, message = "Phone number must be 10 characters long.")
    @Pattern(regexp = "^\0[0-9]" )
    private int phoneNumber;

    public CustomerDto() {
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getUserId() {
        return userId;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }
}
