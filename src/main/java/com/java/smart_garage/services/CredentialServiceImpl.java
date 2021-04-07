package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.CredentialRepository;
import com.java.smart_garage.contracts.serviceContracts.CredentialService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Credential;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialServiceImpl implements CredentialService {

    private final CredentialRepository repository;

    @Autowired
    public CredentialServiceImpl(CredentialRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Credential> getAllCredentials() {
        return repository.getAllCredentials();
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
    public void create(Credential credential, User employeeUser) {
        boolean duplicateExists = true;
        if (!(employeeUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee or the user can create new credential");
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
            throw new DuplicateEntityException("Credential", "name", "user name", credential.getUsername(), credential.getUsername());
        }

        repository.create(credential);

    }

    @Override
    public void update(Credential credential, User employeeUser) {

        boolean duplicateExistsUsername = true;

        if (!(credential.isUser(credential.getUsername()) || employeeUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee or the user can modify credential");
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
            throw new DuplicateEntityException("Credential", "username", credential.getUsername());
        }
        repository.update(credential);
    }

    @Override
    public void delete(int id, User userCredential) {

        if (!userCredential.isEmployee()) {
            throw new UnauthorizedOperationException("Only employee can delete credentials.");
        }
        Credential deletedCredential = new Credential();
        try {
            deletedCredential = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Credential", "id", id);
        }
        repository.delete(id);
    }
}
