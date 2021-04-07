package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getById(int id);

    void create(User user);

    void update(User user);

    void delete(int id, User user);

    void filterUsersByFirstName(String searchKey);

}
