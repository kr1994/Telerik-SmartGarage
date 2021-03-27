package com.java.smart_garage.models;

import javax.persistence.*;
import java.util.List;

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
    private List<CarService> carServiceId;

    public Invoice() {
    }


}

