package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "manufacturers")
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manufacturers_id")
    private int manufacturerId;

    @Column(name = "name")
    private String manufacturerName;

    public Manufacturer() {
    }

    public Manufacturer(int manufacturerId, String manufacturerName) {
        this.manufacturerId = manufacturerId;
        this.manufacturerName = manufacturerName;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }
}
