package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.ManufacturerRepository;
import com.java.smart_garage.contracts.serviceContracts.ManufactureService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Manufacturer;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufactureServiceImpl implements ManufactureService {

    private final ManufacturerRepository repository;

    @Autowired
    public ManufactureServiceImpl(ManufacturerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return repository.getAllManufacturers();
    }

    @Override
    public Manufacturer getById(int id) {
        return repository.getById(id);
    }

    @Override
    public Manufacturer getByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public void create(Manufacturer manufacturer, User employeeUser) {
        boolean duplicateExists = true;

        if (!(employeeUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create a new manufacturer.");
        }
        try {
            repository.getByName(manufacturer.getManufacturerName());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Manufacturer", "name", manufacturer.getManufacturerName());
        }

        repository.create(manufacturer);
    }

    @Override
    public void delete(int id, User employeeUser) {
        if (!(employeeUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee  can delete a manufacturer.");
        }
        repository.delete(id);
    }


}
