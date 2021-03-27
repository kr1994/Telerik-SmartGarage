package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "horse_powers")
public class HorsePower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "power_id")
    private int powerId;

    @Column(name = "power")
    private int power;

    public HorsePower() {
    }

    public HorsePower(int powerId, int power) {
        this.powerId = powerId;
        this.power = power;
    }

    public void setPowerId(int powerId) {
        this.powerId = powerId;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public int getPowerId() {
        return powerId;
    }
}


