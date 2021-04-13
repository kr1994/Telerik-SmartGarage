package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.ColoursRepository;
import com.java.smart_garage.contracts.serviceContracts.ColourService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Colour;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColourServiceImpl implements ColourService {
    private final ColoursRepository repository;

    @Autowired
    public ColourServiceImpl(ColoursRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Colour> getAllColours() {
        return repository.getAllColours();
    }

    @Override
    public Colour getById(int id) {
        return repository.getById(id);
    }

    @Override
    public Colour getByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public void create(Colour colour, User user) {
        boolean duplicateExists = true;

        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create a new colour.");
        }

        try {
            repository.getByName(colour.getColour());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Colour", "name", colour.getColour());
        }

        repository.create(colour);
    }

    @Override
    public void delete(int id, User user) {
        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee  can delete a colour.");
        }
        repository.delete(id);
    }
}
