package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.User;
import com.java.smart_garage.models.PersonalInfo;
import com.java.smart_garage.models.Credential;
import com.java.smart_garage.models.UserType;

import java.util.List;

public interface UserRepository {

    List<User> getAllUsers();

    User getById(int id);

    User create(User user);

    User update(User user,
                Credential credential,
                PersonalInfo personalInfo,
                UserType userType);

    void delete(int id);

    void filterUsersByFirstName(String searchKey);

}
