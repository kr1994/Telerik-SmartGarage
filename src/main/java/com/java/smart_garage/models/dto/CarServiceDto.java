package com.java.smart_garage.models.dto;

import com.java.smart_garage.models.Invoice;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CarServiceDto {

    @NotNull
    @Positive(message = "Car Id must be positive.")
    private int carId;

    @NotNull
    @Positive(message = "Service Id must be positive.")
    private int serviceId;

    @NotNull
    private InvoiceDto invoice;

    public CarServiceDto() {
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public InvoiceDto getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDto invoice) {
        this.invoice = invoice;
    }
}
