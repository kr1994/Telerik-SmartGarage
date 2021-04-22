package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.ModelCar;
import com.java.smart_garage.models.User;

import java.util.List;

public interface ModelService {
    List<ModelCar> getAllModels();

    ModelCar getModelById(int id);

    ModelCar getModelByName(String name);

    void create(ModelCar modelCar, User credentialUser);

    void delete(int id, User credentialUser);
}
