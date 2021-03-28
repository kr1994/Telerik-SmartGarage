package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.User;
import com.java.smart_garage.models.UserType;

import java.util.List;

public interface UserRepository {

    List<User> getAll();

    User getById(int id);

    User getByName(String firstName);

    User create(User user);

    User update(User user,
                String username,
                String password,
                String firstName,
                String lastName,
                String email,
                UserType userType);

    void delete(int id);
}
