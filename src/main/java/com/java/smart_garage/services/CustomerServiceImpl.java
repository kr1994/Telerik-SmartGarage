package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.CustomerRepository;
import com.java.smart_garage.contracts.serviceContracts.CustomerService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Customer;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Customer> getAllCustomers(){
        return repository.getAllCustomers();
    }

    @Override
    public Customer getById(int id){
        return repository.getById(id);
    }

    @Override
    public void create(Customer customer, User user) {
        boolean duplicateExists = true;

        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create new customer.");
        }

        try {
            repository.getById(customer.getCustomerId());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Customer", "id", customer.getCustomerId());
        }

        repository.create(customer);
    }

    @Override
    public void delete(int id, User user) {
        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can delete customer.");
        }
        Customer customer = new Customer();
        try {
            customer = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Customer", "id", id);
        }
        repository.delete(id);
    }
}



