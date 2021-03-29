package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Customer;
import com.java.smart_garage.models.User;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer getById(int id);

    void create(Customer customer, User user);

    void delete(int id, User user);

}
