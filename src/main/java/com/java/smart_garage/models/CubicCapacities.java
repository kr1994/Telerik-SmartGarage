package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "cubic_capacities")
public class CubicCapacities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cc_id")
    private int cubicCapacityId;

    @Column(name = "cc")
    private int cubicCapacity;

    public CubicCapacities() {
    }

    public CubicCapacities(int cubicCapacityId, int cubicCapacity) {
        this.cubicCapacityId = cubicCapacityId;
        this.cubicCapacity = cubicCapacity;
    }

    public void setCubicCapacityId(int cubicCapacityId) {
        this.cubicCapacityId = cubicCapacityId;
    }

    public void setCubicCapacity(int cubicCapacity) {
        this.cubicCapacity = cubicCapacity;
    }

    public int getCubicCapacity() {
        return cubicCapacity;
    }

    public int getCubicCapacityId() {
        return cubicCapacityId;
    }

}

