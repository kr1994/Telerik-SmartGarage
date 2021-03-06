package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.CarService;
import com.java.smart_garage.models.Invoice;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface CarServiceRepository {
    List<CarService> getAllCarServices();

    CarService getById(int id);

    CarService create(CarService carService);

    CarService create(CarService carService, Invoice invoice);

    List<CarService> getAllCarServicesByCustomer(int id);

    List<CarService> getCarServicesPrice(int id);

    List<CarService> getAllCarServicesByCar(int id);

    List<CarService> filterByDateAndCarId(Optional<Date> startingDate, Optional<Date> endingDate, int id);

    void delete(int id);
}
