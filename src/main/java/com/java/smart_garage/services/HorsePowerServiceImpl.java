package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.HorsePowerRepository;
import com.java.smart_garage.contracts.serviceContracts.HorsePowerService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.HorsePower;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorsePowerServiceImpl implements HorsePowerService {

    private final HorsePowerRepository repository;

    @Autowired
    public HorsePowerServiceImpl(HorsePowerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<HorsePower> getAllCubicCapacities() {
        return repository.getAllHorsePowers();
    }

    @Override
    public HorsePower getById(int id) {
        return repository.getById(id);
    }

    @Override
    public void create(HorsePower horsePower, User employeeUser) {

        boolean duplicateExists = true;
        if (!(employeeUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create new horse power");
        }
        try {
            repository.getById(horsePower.getPowerId());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new DuplicateEntityException("Horse Power", "power", horsePower.getPower());
        }
        repository.create(horsePower);
    }

    @Override
    public void delete(int id, User employeeUser) {
        if (!(employeeUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can delete horse power!");
        }
        HorsePower horsePower = new HorsePower();
        try {
            horsePower = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Horse Power", "power", horsePower.getPower());
        }

        repository.delete(id);
    }


}
