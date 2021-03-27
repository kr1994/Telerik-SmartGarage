package com.java.smart_garage.models;


import javax.persistence.*;

@Entity
@Table(name = "models")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private int modelId;

    @Column(name = "name")
    private String modelName;

    @ManyToOne
    @Column(name = "manufacturer_id")
    private Manufacturer manufacturer;

    public Model() {
    }

    public Model(int modelId, String modelName, Manufacturer manufacturer) {
        this.modelId = modelId;
        this.modelName = modelName;
        this.manufacturer = manufacturer;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getModelId() {
        return modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }
}
