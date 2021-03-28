package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.Identification;

import java.util.List;

public interface IdentificationRepository {

    List<Identification> getAll();

    Identification getById(int id);

    Identification getByName(String name);

    Identification create(Identification identification);

    void delete(int id);
}
