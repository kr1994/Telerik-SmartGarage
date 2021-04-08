package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Model;
import com.java.smart_garage.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    User getById(int id);

    void create(User user);

    void update(User user);

    void delete(int id, User user);

    void filterCustomers(Optional<String> firstName,
                         Optional<String> lastName,
                         Optional<String> email,
                         Optional<String> phoneNumber,
                         Optional<Model> modelCar,
                         Optional<Integer> visitsInRange);

}
