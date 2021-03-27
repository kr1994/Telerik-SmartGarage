package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "registration_plates")
public class RegistrationPlate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plate_id")
    private int plateId;

    @Column(name = "plate_number")
    private String plateNumber;

    public RegistrationPlate() {
    }

    public RegistrationPlate(int plateId, String plateNumber) {
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