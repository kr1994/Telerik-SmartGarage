package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.UserRepository;
import com.java.smart_garage.contracts.serviceContracts.UserService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.viewDto.CustomerViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    @Override
    public User getById(int id) {
        return repository.getById(id);
    }

    @Override
    public void create(User user) {
        boolean duplicateExists = true;

        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee or the user can create new user.");
        }

        try {
            repository.getById(user.getUserId());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("User", "id", user.getUserId());
        }

        repository.create(user);
    }

    //In progress
    @Override
    public void update(User user) {
        boolean duplicateExists = true;

        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee or the user can update user.");
        }

        try {
            repository.getById(user.getUserId());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        repository.update(user, user.getCredential(), user.getPersonalInfo(), user.getUserType());
    }

    @Override
    public void delete(int id, User userCredential) {
        if (!(userCredential.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can delete user.");
        }
        repository.delete(id);
    }

    @Override
    public List<CustomerViewDto> filterCustomers(Optional<String> firstName,
                                                 Optional<String> lastName,
                                                 Optional<String> email,
                                                 Optional<String> phoneNumber,
                                                 Optional<String> model,
                                                 Optional<Date> dateFrom,
                                                 Optional<Date> dateTo,
                                                 User userCredential) {

        if (!(userCredential.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can filter customers.");
        }
        return repository.filterCustomers(firstName, lastName, email, phoneNumber, model, dateFrom, dateTo);
    }

    @Override
    public List<CustomerViewDto> sortCustomersByName(boolean ascending, User userCredential) {
        if (!(userCredential.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can sort customers.");
        }
        return repository.sortCustomersByName(ascending);
    }

    @Override
    public List<CustomerViewDto> sortCustomersByVisits(boolean ascending, User userCredential) {
        if (!(userCredential.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can sort customers.");
        }
        return repository.sortCustomersByVisits(ascending);
    }

}



