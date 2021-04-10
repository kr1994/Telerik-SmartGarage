package com.java.smart_garage.models.viewDto;

import java.util.List;

public class CarServiceViewDto {


    private String carModel;

    private String carManufacturer;

    private String carRegPlate;

    private String carIdNumber;

    private String carOwner;

    private String carOwnerEmail;

    private List<WorkServiceView> workServices;

    private double totalPrice;


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

    public void setWorkServices(List<WorkServiceView> workServices) {
        this.workServices = workServices;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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

    public List<WorkServiceView> getWorkServices() {
        return workServices;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
