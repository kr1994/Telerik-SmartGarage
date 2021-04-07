package com.java.smart_garage.models.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;


public class CredentialDto {

    @NotBlank
    private String username;

    @NotBlank
    @Length(min = 8, max = 30)
    private String password;

    public CredentialDto() {
    }

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
