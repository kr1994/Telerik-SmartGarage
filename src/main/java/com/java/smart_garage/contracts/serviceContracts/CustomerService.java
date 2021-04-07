package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.User;
import com.java.smart_garage.models.Credential;

import java.util.List;

public interface CustomerService {

    List<User> getAllCustomers();

    User getById(int id);

    void create(User user, Credential credential);

    void update(User user, Credential credential);

    void delete(int id, Credential credential);

}
