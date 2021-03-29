package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.UserRepository;
import com.java.smart_garage.contracts.serviceContracts.UserService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAllUsers(){
        return repository.getAllUsers();
    }

    @Override
    public User getById(int id){
        return repository.getById(id);
    }

    @Override
    public User getByUsername(String username){return repository.getByUsername(username);}

    @Override
    public User getByEmail(String email){return repository.getByEmail(email);}

    @Override
    public void create(User user, User employeeUser) {
        boolean duplicateExists = true;
        if (!(employeeUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee or the user can create the user");
        }
        try {
            repository.getByUsername(user.getUsername());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        try {
            User existingUser = repository.getByEmail(user.getEmail());
            if (existingUser.getEmail().equals(user.getEmail())) {
                duplicateExists = true;
            }
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new DuplicateEntityException("User", "name", "email", user.getUsername(), user.getEmail());
        }

        repository.create(user);

    }
    @Override
    public void update(User user, User employeeUser) {

        boolean duplicateExistsUsername = true;
        boolean duplicateExistsEmail = true;

        if (!(user.isUser(user.getUsername()) || employeeUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee or the user can modify the user");
        }
        try {
            User existingUser = repository.getByUsername(user.getUsername());
            if (existingUser.getUserId() != user.getUserId()) {
                duplicateExistsUsername = true;
            }
        } catch (EntityNotFoundException e) {
            duplicateExistsUsername = false;
        }
        try {
            User existingUser = repository.getByEmail(user.getEmail());
            if (existingUser.getEmail().equals(user.getEmail()) && existingUser.getUserId() != user.getUserId()) {
                duplicateExistsEmail = true;
            }

        } catch (EntityNotFoundException e) {
            duplicateExistsEmail = false;
        }

        if (duplicateExistsUsername) {
            throw new DuplicateEntityException("User", "username", user.getUsername());
        } else if (duplicateExistsEmail) {
            throw new DuplicateEntityException("User", "email", user.getEmail());
        }

        repository.update(user);
    }
 }
