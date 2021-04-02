package com.java.smart_garage.models.dto;

import java.time.LocalDate;


public class InvoiceDto {

    private LocalDate date;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
