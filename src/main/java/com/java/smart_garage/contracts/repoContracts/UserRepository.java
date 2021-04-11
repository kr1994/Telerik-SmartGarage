package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.*;
import com.java.smart_garage.models.viewDto.CustomerViewDto;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> getAllUsers();

    User getById(int id);

    List<User> getByFirstName(String firstName);

    List<User> getByLastName(String lastName);

    User getByEmail(String email);

    User create(User user);

    User update(User user,
                Credential credential,
                PersonalInfo personalInfo,
                UserType userType);

    void delete(int id);

    List<CustomerViewDto> filterCustomers(Optional<String> firstName,
                                          Optional<String> lastName,
                                          Optional<String> email,
                                          Optional<String> phoneNumber,
                                          Optional<String> model,
                                          Optional<Date> dateFrom,
                                          Optional<Date> dateTo);

}
