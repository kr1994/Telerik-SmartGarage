package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Model;
import com.java.smart_garage.models.User;

import java.util.List;

public interface ModelsService {
    List<Model> getAllModels();

    Model getModelById(int id);

    Model getModelByName(String name);

    void create(Model model, User user);

    void delete(int id, User user);
}
