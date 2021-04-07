package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.CustomerRepository;
import com.java.smart_garage.contracts.serviceContracts.CustomerService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAllCustomers(){
        return repository.getAllUsers();
    }

    @Override
    public User getById(int id){
        return repository.getById(id);
    }

    @Override
    public void create(User user, Credential credential) {
        boolean duplicateExists = true;

        if (!(credential.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create new customer.");
        }

        try {
            repository.getById(user.getUserId());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Customer", "id", user.getUserId());
        }

        repository.create(user);
    }

    //In progress
    @Override
    public void update(User user, Credential credential) {
        boolean duplicateExists = true;

        if (!(credential.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can update new customer.");
        }

        try {
            repository.getById(user.getUserId());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        repository.update(user, credential, user.getPersonalInfo());
    }

    @Override
    public void delete(int id, Credential credential) {
        if (!(credential.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can delete customer.");
        }
        User user = new User();
        try {
            user = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Customer", "id", id);
        }
        repository.delete(id);
    }
}



