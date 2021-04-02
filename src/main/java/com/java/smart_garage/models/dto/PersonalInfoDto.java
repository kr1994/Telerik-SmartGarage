package com.java.smart_garage.models.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class PersonalInfoDto {

    @NotBlank
    @Length(min = 2, max = 20)
    private String firstName;

    @NotBlank
    @Length(min = 2, max = 20)
    private String lastName;

    @NotBlank
    @Email(message = "It doesn't look like email.")
    private String email;

    public PersonalInfoDto() {
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

}
