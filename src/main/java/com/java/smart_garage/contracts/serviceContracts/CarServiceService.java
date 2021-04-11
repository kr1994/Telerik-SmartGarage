package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.CarService;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.viewDto.WorkServiceView;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface CarServiceService {  // Too complex name

    List<CarService> getAllCarServices();

    List<WorkServiceView> getAllCarServicesByView(Optional<Date> startingDate, Optional<Date> endingDate,int id,Optional<String> currency);

    List<CarService> getAllCarServicesByCustomer(int id);

    List<CarService> getAllCarServicesByCar(int id);

    //List<WorkServiceView> filterByDateAndCarId(Optional<Date> startingDate, Optional<Date> endingDate, int id, Optional<String> currency);

    double getCarServicesPrice(int id);

    CarService getById(int id);

    void create(CarService carService, User user);

    void delete(int id, User user);

}
