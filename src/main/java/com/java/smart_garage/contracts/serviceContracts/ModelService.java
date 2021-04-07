package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Model;
import com.java.smart_garage.models.Credential;

import java.util.List;

public interface ModelService {
    List<Model> getAllModels();

    Model getModelById(int id);

    Model getModelByName(String name);

    void create(Model model, Credential credential);

    void delete(int id, Credential credential);
}
