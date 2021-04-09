package com.java.smart_garage.models.viewDto;

import java.util.Date;

public class CarServiceViewDto {


    private String carModel;

    private String carManufacturer;

    private String carRegPlate;

    private String carIdNumber;

    private String carOwner;

    private String carOwnerEmail;

    private String serviceName;

    private double servicePrice;

    private Date serviceDate;


    public CarServiceViewDto() {
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setCarManufacturer(String carManufacturer) {
        this.carManufacturer = carManufacturer;
    }

    public void setCarRegPlate(String carRegPlate) {
        this.carRegPlate = carRegPlate;
    }

    public void setCarIdNumber(String carIdNumber) {
        this.carIdNumber = carIdNumber;
    }

    public void setCarOwner(String carOwner) {
        this.carOwner = carOwner;
    }

    public void setCarOwnerEmail(String carOwnerEmail) {
        this.carOwnerEmail = carOwnerEmail;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarManufacturer() {
        return carManufacturer;
    }

    public String getCarRegPlate() {
        return carRegPlate;
    }

    public String getCarIdNumber() {
        return carIdNumber;
    }

    public String getCarOwner() {
        return carOwner;
    }

    public String getCarOwnerEmail() {
        return carOwnerEmail;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public Date getServiceDate() {
        return serviceDate;
    }
}
