package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.UserType;

import java.util.List;

public interface UserTypeRepository {
    List<UserType> getAllUserTypes();

    UserType getById(int id);

    UserType getByName(String name);

    UserType create(UserType type);

    void delete(int id);
}
