package com.java.smart_garage.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private int invoiceId;

    @OneToOne
    @Column(name = "customer_id")
    private Customer customer;

    @OneToMany
    @JoinTable(name = "car_services")
    private Set<CarService> carService;

    public Invoice() {
    }

    public Invoice(int invoiceId, Customer customer, Set<CarService> carService) {
        this.invoiceId = invoiceId;
        this.customer = customer;
        this.carService = carService;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setCarService(Set<CarService> carService) {
        this.carService = carService;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Set<CarService> getCarService() {
        return carService;
    }
}

