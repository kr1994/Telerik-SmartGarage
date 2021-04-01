package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "car_services")
public class CarService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int carServicesId;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private WorkService service;

    public CarService() {
    }

    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;


    public CarService(int carServicesId, Car car, WorkService service, Invoice invoice) {
        this.carServicesId = carServicesId;
        this.car = car;
        this.service = service;
        this.invoice = invoice;
    }


    public void setCarServicesId(int carServicesId) {
        this.carServicesId = carServicesId;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setService(WorkService service) {
        this.service = service;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public int getCarServicesId() {
        return carServicesId;
    }

    public Car getCar() {
        return car;
    }

    public WorkService getService() {
        return service;
    }

    public Invoice getInvoice() {
        return invoice;
    }
}
