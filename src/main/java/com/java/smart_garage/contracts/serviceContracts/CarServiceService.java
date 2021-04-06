package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.CarService;
import com.java.smart_garage.models.User;

import java.util.List;

public interface CarServiceService  {  // Too complex name

    List<CarService> getAllCarServices();

    List<CarService> getAllCarServicesByCustomer(int id);

    List<CarService> getAllCarServicesByCar(int id);

    double getCarServicesPrice(int id);

    CarService getById(int id);

    void create(CarService carService, User user);

    public void delete(int id, User user);

}
