package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.AutomobileRepository;
import com.java.smart_garage.contracts.serviceContracts.AutomobileService;
import com.java.smart_garage.contracts.serviceContracts.PlateValidationService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.IncorrectPlateRegistrationException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Automobile;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutomobileServiceImpl implements AutomobileService {
    private final AutomobileRepository repository;
    private final PlateValidationService plateValidationService;


    @Autowired
    public AutomobileServiceImpl(AutomobileRepository repository, PlateValidationService plateValidationService) {
        this.repository = repository;

        this.plateValidationService = plateValidationService;
    }

    @Override
    public List<Automobile> getAllCars() {
        return repository.getAllCars();
    }

    @Override
    public List<Automobile> getAllCarsByOwner(int id) {
        return repository.customerId(id);
    }

    @Override
    public Automobile getById(int id) {
        return repository.getById(id);
    }

    @Override
    public void create(Automobile automobile, User user) {
        validation(automobile, user);
        repository.create(automobile);
    }

    public void update(Automobile automobile, User user) {
        validation(automobile, user);
        repository.update(automobile);
    }

    @Override
    public void delete(int id, User user) {
        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can delete a car.");
        }
        repository.delete(id);
    }


    private void validation(Automobile automobile, User user) {
        boolean duplicateExistsIdentification = true;
        boolean duplicateExistsIPlate = true;

        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create a new car.");
        }

        try {
            repository.getByIdentifications(automobile.getIdentifications());
        } catch (EntityNotFoundException e) {
            duplicateExistsIdentification = false;
        }
        try {
            repository.getByPlate(automobile.getRegistrationPlate());
        } catch (EntityNotFoundException e) {
            duplicateExistsIPlate = false;
        }


        if (duplicateExistsIdentification) {
            throw new DuplicateEntityException("Car", "identification number", automobile.getIdentifications());
        }
        if (duplicateExistsIPlate) {
            throw new DuplicateEntityException("Car", "plate", automobile.getRegistrationPlate());
        }
        if (!plateValidationService.trueCityIndexPlate(automobile.getRegistrationPlate())) {
            throw new IncorrectPlateRegistrationException(automobile.getRegistrationPlate());
        }
        if (!plateValidationService.trueNumberPlate(automobile.getRegistrationPlate()))
            throw new IncorrectPlateRegistrationException(automobile.getRegistrationPlate());

        if (!plateValidationService.check(automobile.getRegistrationPlate())) {
            throw new IncorrectPlateRegistrationException(automobile.getRegistrationPlate());
        }
    }

}
