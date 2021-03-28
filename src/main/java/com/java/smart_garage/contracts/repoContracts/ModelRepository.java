package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.Model;

import java.util.List;

public interface ModelRepository {
    List<Model> getAllModels();

    Model getById(int id);

    Model getByName(String name);

    Model create(Model model);

    void delete(int id);
}
