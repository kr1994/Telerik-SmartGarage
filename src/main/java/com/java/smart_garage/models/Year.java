package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "years")
public class Year {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "years_id")
    private int yearId;

    @Column(name = "year")
    private int year;

    public Year() {
    }

    public Year(int yearId, int year) {
        this.yearId = yearId;
        this.year = year;
    }

    public void setYearId(int yearId) {
        this.yearId = yearId;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYearId() {
        return yearId;
    }

    public int getYear() {
        return year;
    }
}
