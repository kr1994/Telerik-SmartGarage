package com.java.smart_garage.controllers.rest;

import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.contracts.serviceContracts.CustomerService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.Customer;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.dto.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("smartgarage/customers")
public class CustomerController {

    private final CustomerService service;
    private final ModelConversionHelper modelConversionHelper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public CustomerController(CustomerService service,
                              ModelConversionHelper modelConversionHelper,
                              AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.modelConversionHelper = modelConversionHelper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping("/")
    public List<Customer> getAllCustomers(){
        return service.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getById(@PathVariable int id) {
        try {
            return service.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Customer create(@RequestHeader HttpHeaders headers, @Valid @RequestBody CustomerDto customerDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Customer customer = modelConversionHelper.customerFromDto(customerDto);
            service.create(customer, user);
            return customer;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.delete(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


}

