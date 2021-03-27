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

    public Model() {
    }

    public Model(int modelId, String modelName) {
        this.modelId = modelId;
        this.modelName = modelName;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getModelId() {
        return modelId;
    }

    public String getModelName() {
        return modelName;
    }
}
