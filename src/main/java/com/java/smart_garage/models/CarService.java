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
    private Automobile automobile;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private WorkService service;


    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    public CarService() {
    }

    public CarService(int carServicesId, Automobile automobile, WorkService service, Invoice invoice) {
        this.carServicesId = carServicesId;
        this.automobile = automobile;
        this.service = service;
        this.invoice = invoice;
    }


    public void setCarServicesId(int carServicesId) {
        this.carServicesId = carServicesId;
    }

    public void setCar(Automobile automobile) {
        this.automobile = automobile;
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

    public Automobile getCar() {
        return automobile;
    }

    public WorkService getService() {
        return service;
    }

    public Invoice getInvoice() {
        return invoice;
    }
}
