package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.CarService;

import java.util.List;

public interface CarServiceRepository {
    List<CarService> getAllCarServices();

    CarService getById(int id);

    CarService create(CarService carService);

    List<CarService> getAllCarServicesByCustomer(int id);

    double getCarServicesPrice(int id);

    List<CarService> getAllCarServicesByCar(int id);

    void delete(int id);
}
