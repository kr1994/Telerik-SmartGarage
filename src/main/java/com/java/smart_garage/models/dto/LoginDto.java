package com.java.smart_garage.models.dto;

import javax.validation.constraints.NotEmpty;

public class LoginDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}
