package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.UserRepository;
import com.java.smart_garage.contracts.serviceContracts.UserService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.User;
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
    public List<User> getAllUsers(){
        return repository.getAllUsers();
    }

    @Override
    public User getById(int id){
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
        User user = new User();
        try {
            user = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("User", "id", id);
        }
        repository.delete(id);
    }

    public void filterUsersByFirstName(String searchKey) {
        repository.filterUsersByFirstName(searchKey);
    }

}



