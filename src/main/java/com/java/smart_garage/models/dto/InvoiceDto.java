package com.java.smart_garage.models.dto;

import com.java.smart_garage.models.CarService;
import com.java.smart_garage.models.Customer;

import javax.validation.constraints.NotNull;
import java.util.List;


public class InvoiceDto {

    private Customer customer;

    @NotNull
    private List<CarService> carService;

    public InvoiceDto() {
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setCarService(List<CarService> carService) {
        this.carService = carService;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<CarService> getCarService() {
        return carService;
    }
}
