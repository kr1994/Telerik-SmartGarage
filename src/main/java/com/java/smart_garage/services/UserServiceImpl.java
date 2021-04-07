package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.UserRepository;
import com.java.smart_garage.contracts.serviceContracts.UserService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Credential> getAllUsers() {
        return repository.getAllUsers();
    }

    @Override
    public Credential getById(int id) {
        return repository.getById(id);
    }

    @Override
    public Credential getByUsername(String username) {
        return repository.getByUsername(username);
    }

    @Override
    public void create(Credential credential, Credential employeeCredential) {
        boolean duplicateExists = true;
        if (!(employeeCredential.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee or the user can create the user");
        }
        try {
            repository.getByUsername(credential.getUsername());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        try {
            Credential existingCredential = repository.getByUsername(credential.getUsername());
            if (existingCredential.getUsername().equals(credential.getUsername())) {
                duplicateExists = true;
            }
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new DuplicateEntityException("User", "name", "user name", credential.getUsername(), credential.getUsername());
        }

        repository.create(credential);

    }

    @Override
    public void update(Credential credential, Credential employeeCredential) {

        boolean duplicateExistsUsername = true;


        if (!(credential.isUser(credential.getUsername()) || employeeCredential.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee or the user can modify the user");
        }
        try {
            Credential existingCredential = repository.getByUsername(credential.getUsername());
            if (existingCredential.getCredentialId() != credential.getCredentialId()) {
                duplicateExistsUsername = true;
            }
        } catch (EntityNotFoundException e) {
            duplicateExistsUsername = false;
        }


        if (duplicateExistsUsername) {
            throw new DuplicateEntityException("User", "username", credential.getUsername());
        }
        repository.update(credential);
    }

    @Override
    public void delete(int id, Credential credential) {

        if (!credential.isEmployee()) {
            throw new UnauthorizedOperationException("Only employee can delete users.");
        }
        Credential deletedCredential = new Credential();
        try {
            deletedCredential = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("User", "id", id);
        }
        repository.delete(id);
    }
}
