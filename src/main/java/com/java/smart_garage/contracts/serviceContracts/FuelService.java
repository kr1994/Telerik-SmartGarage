package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Fuel;
import com.java.smart_garage.models.Credential;

import java.util.List;

public interface FuelService {
    List<Fuel> getAllFuels();

    Fuel getById(int id);

    Fuel getByName(String name);

    void create(Fuel fuel, Credential credential);

    void delete(int id, Credential credential);
}
