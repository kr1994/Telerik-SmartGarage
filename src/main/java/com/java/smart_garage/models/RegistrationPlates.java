package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "registration_plates")
public class RegistrationPlates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plate_id")
    private int plateId;

    @Column(name = "plate_number")
    private String plateNumber;

    public RegistrationPlates() {
    }

    public RegistrationPlates(int plateId, String plateNumber) {
        this.plateId = plateId;
        this.plateNumber = plateNumber;
    }


    public void setPlateId(int plateId) {
        this.plateId = plateId;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public int getPlateId() {
        return plateId;
    }
}