package com.java.smart_garage.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class UserDto {

    @NotNull
    @Positive(message = "User Id must be positive.")
    private int userId;

    @NotNull
    @Positive(message = "Personal Info Id must be positive.")
    private int personalInfoId;


    @Positive(message = "User type Id must be positive.")
    private int userType;


    public UserDto() {
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPersonalInfoId(int personalInfoId) {
        this.personalInfoId = personalInfoId;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public int getPersonalInfoId() {
        return personalInfoId;
    }

    public int getUserType() {
        return userType;
    }
}
