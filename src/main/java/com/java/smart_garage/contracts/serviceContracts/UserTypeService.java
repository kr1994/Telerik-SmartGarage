package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.User;
import com.java.smart_garage.models.UserType;

import java.util.List;

public interface UserTypeService {
    List<UserType> getAllTypes();

    UserType getById(int id);

    UserType getByName(String name);

    void create(UserType type, User credentialUser);

    void delete(int id, User credentialUser);
}
