package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.CarService;

import java.util.List;

public interface CarServicesRepository {
    List<CarService> getAllCarServices();

    CarService getById(int id);

    CarService create(CarService carService);
}