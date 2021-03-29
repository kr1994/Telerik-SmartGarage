package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.CubicCapacityRepository;
import com.java.smart_garage.contracts.serviceContracts.CubicCapacityService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.CubicCapacity;
import com.java.smart_garage.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CubicCapacityServiceImpl implements CubicCapacityService {

    private final CubicCapacityRepository repository;

    public CubicCapacityServiceImpl(CubicCapacityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CubicCapacity> getAllCubicCapacities() {
        return repository.getAllCubicCapacities();
    }

    @Override
    public CubicCapacity getById(int id) {
        return repository.getById(id);
    }

    @Override
    public void create(CubicCapacity cubicCapacity, User employeeUser) {

        boolean duplicateExists = true;
        if (!(employeeUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create new cubic capacity");
        }
        try {
            repository.getById(cubicCapacity.getCubicCapacityId());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new DuplicateEntityException("Cubic Capacity", "capacity", cubicCapacity.getCubicCapacity());
        }
        repository.create(cubicCapacity);
    }

    @Override
    public void delete(int id, User employeeUser) {
        if (!(employeeUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can delete cubic capacity!");
        }
        CubicCapacity cc = new CubicCapacity();
        try {
            cc = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Cubic Capacity", "capacity", cc.getCubicCapacity());
        }

        repository.delete(id);
    }


}