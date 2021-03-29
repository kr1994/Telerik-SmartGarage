package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Fuel;
import com.java.smart_garage.models.User;

import java.util.List;

public interface FuelService {
    List<Fuel> getAllFuels();

    Fuel getById(int id);

    Fuel getByName(String name);

    void create(Fuel fuel, User user);

    void delete(int id, User user);
}
