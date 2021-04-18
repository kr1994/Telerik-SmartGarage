package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.CarService;
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

    User getByUserName(String userName);

    void create(User user, User userCredential);

    void update(User user, User userCredential);

    void delete(int id, User user);

    List<CarService> filterCustomers(Optional<String> firstName,
                                     Optional<String> lastName,
                                     Optional<String> email,
                                     Optional<String> phoneNumber,
                                     Optional<String> model,
                                     Optional<Date> dateFrom,
                                     Optional<Date> dateTo,
                                     User userCredential);

    List<CustomerViewDto> sortCustomersByName(List<CustomerViewDto> customers, boolean ascending, User userCredential);

    List<CustomerViewDto> sortCustomersByVisits(List<CustomerViewDto> customers, boolean ascending, User userCredential);

    int getUserCount();
}
