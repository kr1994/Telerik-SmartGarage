package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getById(int id);

    User getByUsername(String username);

    User getByEmail(String email);

    void create(User user, User employeeUser);

    void update(User user, User employeeUser);

    void delete(int id, User user);
}
