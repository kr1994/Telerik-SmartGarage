package com.java.smart_garage.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.java.smart_garage.models.UserType;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class UserDto {

    @NotNull
    @Positive(message = "User Id must be positive.")
    private int userId;

    @NotNull
    @Positive(message = "Personal Info Id must be positive.")
    private int personalInfoId;



    public UserDto() {
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPersonalInfoId(int personalInfoId) {
        this.personalInfoId = personalInfoId;
    }

    public int getUserId() {
        return userId;
    }

    public int getPersonalInfoId() {
        return personalInfoId;
    }
}
