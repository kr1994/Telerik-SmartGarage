package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.YearRepository;
import com.java.smart_garage.contracts.serviceContracts.YearsService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.Year;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YearsServiceImpl implements YearsService {
    private final YearRepository repository;

    @Autowired
    public YearsServiceImpl(YearRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Year> getAllYears() {
        return repository.getAllYears();
    }

    @Override
    public Year getById(int id) {
        return repository.getById(id);
    }

    @Override
    public Year getExactYear(int year) {
        return repository.getByYear(year);
    }

    @Override
    public void create(Year year, User user) {
        boolean duplicateExists = true;

        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create a new year.");
        }

        try {
            repository.getByYear(year.getYear());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Year", "name", year.getYear());
        }

        repository.create(year);
    }

    @Override
    public void delete(int id, User user) {
        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee  can delete a year.");
        }
        Year year = new Year();
        try {
            year = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Year", "id", id);
        }
        repository.delete(id);
    }
}
