package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.User;
import com.java.smart_garage.models.UserType;

import java.util.List;

public interface UserRepository {

    List<User> getAllUsers();

    User getById(int id);

    User getByUsername(String username);

    User create(User user);

    User update(User user);

    void delete(int id);
}
