package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.ModelCar;

import java.util.List;

public interface ModelRepository {
    List<ModelCar> getAllModels();

    ModelCar getById(int id);

    ModelCar getByName(String name);

    ModelCar create(ModelCar modelCar);

    void delete(int id);
}
