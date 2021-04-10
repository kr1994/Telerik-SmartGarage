package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.*;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> getAllUsers();

    User getById(int id);

    User create(User user);

    User update(User user,
                Credential credential,
                PersonalInfo personalInfo,
                UserType userType);

    void delete(int id);

    List<PersonalInfo> filterCustomers(Optional<String> firstName,
                               Optional<String> lastName,
                               Optional<String> email,
                               Optional<String> phoneNumber);

}
