package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.*;

import java.util.List;

public interface CarRepository {
    List<Car> getAllCars();

    Car getById(int id);

    Car getByIdentifications(String name);

    Car getByPlate(String name);

    Car create(Car car);

    Car update(Car car);

    void delete(int id);
}
