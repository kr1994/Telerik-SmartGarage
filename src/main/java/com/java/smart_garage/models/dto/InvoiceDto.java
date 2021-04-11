package com.java.smart_garage.models.dto;

import java.time.LocalDate;
import java.sql.Date;


public class InvoiceDto {

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
