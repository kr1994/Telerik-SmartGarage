package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private int invoiceId;

    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "car_service_id")
    private int carServiceId;

    public Invoice() {
    }

    public Invoice(int invoiceId, int customerId, int carServiceId) {
        this.invoiceId = invoiceId;
        this.customerId = customerId;
        this.carServiceId = carServiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setCarServiceId(int carServiceId) {
        this.carServiceId = carServiceId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCarServiceId() {
        return carServiceId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public int getCustomerId() {
        return customerId;
    }
}
