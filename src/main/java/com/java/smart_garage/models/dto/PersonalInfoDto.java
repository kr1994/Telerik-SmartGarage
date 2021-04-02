package com.java.smart_garage.models.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

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

    @NotBlank
    @Length(min = 10, max = 10, message = "Phone number must be 10 characters long.")
    @Pattern(regexp = "^.*08[7-9][2-9]\\d\\d\\d\\d\\d\\d.*$")
    private int phoneNumber;

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

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public int getPhoneNumber() {
        return phoneNumber;
    }

}
