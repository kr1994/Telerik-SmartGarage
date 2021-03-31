package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.*;

import java.util.List;

public interface CarRepository {
    List<Car> getAllCars();

    Car getById(int id);

    Car getByIdentifications(String name);

    Car create(Car car);

    Car update(Car car, Model model,
               String registrationPlate,
               String identification,
               int year,
               Colour colour,
               Engine engine);

    void delete(int id);
}
