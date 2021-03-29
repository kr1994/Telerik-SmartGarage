package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.FuelRepository;
import com.java.smart_garage.contracts.serviceContracts.FuelService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Fuel;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuelServiceImpl implements FuelService {

    private final FuelRepository repository;

    @Autowired
    public FuelServiceImpl(FuelRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Fuel> getAllFuels() {
        return repository.getAllFuels();
    }

    @Override
    public Fuel getById(int id) {
        return repository.getById(id);
    }

    @Override
    public Fuel getByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public void create(Fuel fuel, User user) {
        boolean duplicateExists = true;

        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create a new fuel.");
        }

        try {
            repository.getByName(fuel.getFuelName());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Manufacturer", "name", fuel.getFuelName());
        }

        repository.create(fuel);
    }

    @Override
    public void delete(int id, User user) {
        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee  can delete a manufacturer.");
        }
        Fuel fuel = new Fuel();
        try {
            fuel = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Fuel", "id", id);
        }
        repository.delete(id);
    }
}
