package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.User;
import com.java.smart_garage.models.PersonalInfo;
import com.java.smart_garage.models.Credential;

import java.util.List;

public interface CustomerRepository {

    List<User> getAllUsers();

    User getById(int id);

    User create(User user);

    User update(User user,
                Credential credential,
                PersonalInfo personalInfo);

    void delete(int id);

}
