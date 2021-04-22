package com.java.smart_garage.models;


import javax.persistence.*;

@Entity
@Table(name = "models")
public class ModelCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private int modelId;

    @Column(name = "name")
    private String model;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    public ModelCar() {
    }

    public ModelCar(int modelId, String model, Manufacturer manufacturer) {
        this.modelId = modelId;
        this.model = model;
        this.manufacturer = manufacturer;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public void setModel(String modelName) {
        this.model = modelName;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getModelId() {
        return modelId;
    }

    public String getModel() {
        return model;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }
}
