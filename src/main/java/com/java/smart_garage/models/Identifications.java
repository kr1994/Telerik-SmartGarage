package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "identifications")
public class Identifications {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "identification_id")
    private int identificationId;

    @Column(name = "identification")
    private String identification;

    public Identifications() {
    }

    public Identifications(int identificationId, String identification) {
        this.identificationId = identificationId;
        this.identification = identification;
    }

    public void setIdentificationId(int identificationId) {
        this.identificationId = identificationId;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public int getIdentificationId() {
        return identificationId;
    }

    public String getIdentification() {
        return identification;
    }
}
