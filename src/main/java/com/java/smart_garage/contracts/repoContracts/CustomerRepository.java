package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.Customer;
import com.java.smart_garage.models.User;

import java.util.List;

public interface CustomerRepository {

    List<Customer> getAllCustomers();

    Customer getById(int id);

    Customer create(Customer customer);

    Customer update(Customer customer,
                    User user,
                    int phoneNumber);

    void delete(int id);

}
