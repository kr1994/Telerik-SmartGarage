package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.CarRepository;
import com.java.smart_garage.contracts.serviceContracts.CarService;
import com.java.smart_garage.contracts.serviceContracts.PlateValidationService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.IncorrectPlateRegistrationException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Car;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository repository;
    private final PlateValidationService plateValidationService;


    @Autowired
    public CarServiceImpl(CarRepository repository, PlateValidationService plateValidationService) {
        this.repository = repository;

        this.plateValidationService = plateValidationService;
    }

    @Override
    public List<Car> getAllCars() {
        return repository.getAllCars();
    }

    @Override
    public Car getById(int id) {
        return repository.getById(id);
    }

    @Override
    public void create(Car car, User user) {
        boolean duplicateExists = true;

        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create a new car.");
        }

        try {
            repository.getByIdentifications(car.getIdentifications());
            repository.getByPlate(car.getIdentifications());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (!plateValidationService.trueCityIndexPlate(car.getRegistrationPlate())) {
            throw new IncorrectPlateRegistrationException(car.getRegistrationPlate());
        }
        if (!plateValidationService.trueNumberPlate(car.getRegistrationPlate()))
            throw new IncorrectPlateRegistrationException(car.getRegistrationPlate());

        if (!plateValidationService.check(car.getRegistrationPlate())) {
            throw new IncorrectPlateRegistrationException(car.getRegistrationPlate());
        }

        if (duplicateExists) {
            throw new DuplicateEntityException("Car", "identification number", car.getIdentifications());
        }

        repository.create(car);
    }

    public void update(Car car, User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException("Only employee can update shipment.");
        }

        repository.update(car);
    }

    @Override
    public void delete(int id, User user) {
        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee  can delete a car.");
        }
        Car car = new Car();
        try {
            car = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Car", "id", id);
        }
        repository.delete(id);
    }


}
