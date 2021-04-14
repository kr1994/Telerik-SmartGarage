package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.UserTypeRepository;
import com.java.smart_garage.contracts.serviceContracts.UserTypeService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeServiceImpl implements UserTypeService {
    private final UserTypeRepository repository;

    @Autowired
    public UserTypeServiceImpl(UserTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<UserType> getAllTypes() {
        return repository.getAllUserTypes();
    }

    @Override
    public UserType getById(int id) {
        return repository.getById(id);
    }

    @Override
    public UserType getByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public void create(UserType type, User credentialUser) {
        boolean duplicateExists = true;

        if (!(credentialUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create a new type.");
        }

        try {
            repository.getByName(type.getType());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Type", "name", type.getType());
        }

        repository.create(type);
    }

    @Override
    public void delete(int id, User credentialUser) {
        if (!(credentialUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee  can delete a type.");
        }
        repository.delete(id);
    }
}
