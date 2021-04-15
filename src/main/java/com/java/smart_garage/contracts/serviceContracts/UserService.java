package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Model;
import com.java.smart_garage.models.PersonalInfo;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.viewDto.CustomerViewDto;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    User getById(int id);

    void create(User user);

    void update(User user);

    void delete(int id, User user);

    List<CustomerViewDto> filterCustomers(Optional<String> firstName,
                                          Optional<String> lastName,
                                          Optional<String> email,
                                          Optional<String> phoneNumber,
                                          Optional<String> model,
                                          Optional<Date> dateFrom,
                                          Optional<Date> dateTo,
                                          User userCredential);

    List<CustomerViewDto> sortCustomersByName(boolean ascending, User userCredential);

    List<CustomerViewDto> sortCustomersByVisits(boolean ascending, User userCredential);

    int getUserCount();

    User getByUserName(String userName);
}
