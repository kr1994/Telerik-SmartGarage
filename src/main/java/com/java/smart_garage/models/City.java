package com.java.smart_garage.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "city")
public class City {

    @Id
    @Column(name = "index")
    private String cityIndex;

    public City() {
    }

    public City(String cityIndex) {
        this.cityIndex = cityIndex;
    }

    public void setCityIndex(String cityIndex) {
        this.cityIndex = cityIndex;
    }

    public String getCityIndex() {
        return cityIndex;
    }
}
