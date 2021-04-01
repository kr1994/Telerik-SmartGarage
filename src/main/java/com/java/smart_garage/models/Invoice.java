package com.java.smart_garage.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private int invoiceId;

    @DateTimeFormat
    @Column(name = "date")
    private LocalDate date;



    public Invoice() {
    }

    public Invoice(int invoiceId, LocalDate date) {
        this.invoiceId = invoiceId;
        this.date = date;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public LocalDate getDate() {
        return date;
    }
}


