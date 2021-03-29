package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.RegistrationPlateRepository;
import com.java.smart_garage.contracts.serviceContracts.RegistrationPlateService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.RegistrationPlate;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationPlateServiceImpl implements RegistrationPlateService {

    private final RegistrationPlateRepository repository;

    @Autowired
    public RegistrationPlateServiceImpl(RegistrationPlateRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<RegistrationPlate> getAllRegistrationPlates() {
        return repository.getAllRegistrationPlates();
    }

    @Override
    public RegistrationPlate getById(int id) {
        return repository.getById(id);
    }

    @Override
    public RegistrationPlate getByName(String number){
        return repository.getByName(number);
    }

    @Override
    public void create(RegistrationPlate registrationPlate, User employeeUser) {

        boolean duplicateExists = true;
        if (!(employeeUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create new registration plate");
        }
        try {
            repository.getByName(registrationPlate.getPlateNumber());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new DuplicateEntityException("Registration Plate", "plate number", registrationPlate.getPlateNumber());
        }
        repository.create(registrationPlate);
    }

    @Override
    public void delete(int id, User employeeUser) {
        if (!(employeeUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can delete registration plate!");
        }
        RegistrationPlate registrationPlate = new RegistrationPlate();
        try {
            registrationPlate = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Registration Plate", "id", id);
        }

        repository.delete(id);
    }


}

