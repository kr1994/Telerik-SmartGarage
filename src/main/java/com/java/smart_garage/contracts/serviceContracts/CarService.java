package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Car;
import com.java.smart_garage.models.User;

import java.util.List;

public interface CarService {
    List<Car> getAllCars();

    Car getById(int id);

    void create(Car car, User user);

    void delete(int id, User user);
}
