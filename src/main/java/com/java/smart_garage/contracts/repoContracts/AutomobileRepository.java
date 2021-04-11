package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.*;

import java.util.List;

public interface AutomobileRepository {
    List<Automobile> getAllCars();

    Automobile getById(int id);

    Automobile getByIdentifications(String name);

    Automobile getByPlate(String name);

    List<Automobile> customerId(int id);

    Automobile create(Automobile automobile);

    Automobile update(Automobile automobile);

    void delete(int id);
}
