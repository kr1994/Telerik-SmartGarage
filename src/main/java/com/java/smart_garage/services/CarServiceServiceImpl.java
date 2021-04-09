package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.CarServiceRepository;
import com.java.smart_garage.contracts.serviceContracts.CarServiceService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.CarService;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceServiceImpl implements CarServiceService {

    private final CarServiceRepository repository;

    @Autowired
    public CarServiceServiceImpl(CarServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CarService> getAllCarServices(){
        return repository.getAllCarServices();
    }

    @Override
    public CarService getById(int id){
        return repository.getById(id);
    }

    @Override
    public List<CarService> getAllCarServicesByCustomer(int id) {return repository.getAllCarServicesByCustomer(id);}

    @Override
    public List<CarService> getAllCarServicesByCar(int id) {return repository.getAllCarServicesByCar(id);}

    @Override
    public double getCarServicesPrice(int id) {return repository.getCarServicesPrice(id);}

    @Override
    public List<CarService> filterByDateAndCarId(Optional<Date> startingDate, Optional<Date> endingDate, int id){
        return  repository.filterByDateAndCarId(startingDate,endingDate,id);
    }

    @Override
    public void create(CarService carService, User user) {
        boolean duplicateExists = true;

        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create a new car service.");
        }

        try {
            repository.getById(carService.getCarServicesId());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Car Service", "id", carService.getCarServicesId());
        }

        repository.create(carService);
    }

    @Override
    public void delete(int id, User user) {
        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee  can delete a car.");
        }
        CarService carService = new CarService();
        try {
            carService = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Car", "id", id);
        }
        repository.delete(id);
    }

}



