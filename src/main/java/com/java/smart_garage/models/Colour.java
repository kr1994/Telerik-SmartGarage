package com.java.smart_garage.models;

import javax.persistence.*;

@Entity
@Table(name = "colours")
public class Colour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "colour_id")
    private int colourId;

    @Column(name = "colour")
    private String colour;

    public Colour() {
    }

    public Colour(int colourId, String colour) {
        this.colourId = colourId;
        this.colour = colour;
    }

    public void setColourId(int colourId) {
        this.colourId = colourId;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getColour() {
        return colour;
    }

    public int getColourId() {
        return colourId;
    }
}

