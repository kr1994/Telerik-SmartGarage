package com.java.smart_garage.models.dto;

import com.java.smart_garage.models.UserType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


public class UserDto {

    @NotBlank
    private String username;

    @NotBlank
    @Length(min = 8, max = 30)
    private String password;

    private UserType userType;

    public UserDto() {
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }
}
