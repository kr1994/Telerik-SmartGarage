package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Car;
import com.java.smart_garage.models.Credential;

import java.util.List;

public interface CarService {
    List<Car> getAllCars();

    Car getById(int id);

    void create(Car car, Credential credential);

    void update(Car car, Credential credential);

    void delete(int id, Credential credential);
}
