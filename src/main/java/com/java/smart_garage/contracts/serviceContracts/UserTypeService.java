package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Credential;
import com.java.smart_garage.models.UserType;

import java.util.List;

public interface UserTypeService {
    List<UserType> getAllTypes();

    UserType getById(int id);

    UserType getByName(String name);

    void create(UserType type, Credential credential);

    void delete(int id, Credential credential);
}
