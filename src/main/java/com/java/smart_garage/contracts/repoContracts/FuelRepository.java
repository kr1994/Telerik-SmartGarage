package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.Fuel;

import java.util.List;

public interface FuelRepository {
    List<Fuel> getAllFuels();

    Fuel getById(int id);

    Fuel getByName(String name);

    Fuel create(Fuel fuel);

    void delete(int id);
}
