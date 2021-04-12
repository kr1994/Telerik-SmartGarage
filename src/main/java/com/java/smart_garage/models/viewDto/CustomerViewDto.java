package com.java.smart_garage.models.viewDto;

import com.java.smart_garage.models.Model;
import com.java.smart_garage.models.UserType;

import java.sql.Date;
import java.util.List;

public class CustomerViewDto {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String carModel;

    private List<Date> visitsInRange;

    private UserType userType;

    public CustomerViewDto() {

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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setVisitsInRange(List<Date> visitsInRange) {
        this.visitsInRange = visitsInRange;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCarModel() {
        return carModel;
    }

    public List<Date> getVisitsInRange() {
        return visitsInRange;
    }
}
