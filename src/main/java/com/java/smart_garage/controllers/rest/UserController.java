package com.java.smart_garage.controllers.rest;

import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.contracts.serviceContracts.UserService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.*;
import com.java.smart_garage.models.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("smartgarage/users")
public class UserController {

    private final UserService service;
    private final ModelConversionHelper modelConversionHelper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public UserController(UserService service,
                          ModelConversionHelper modelConversionHelper,
                          AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.modelConversionHelper = modelConversionHelper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping("/")
    public List<User> getAllUsers(){
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id) {
        try {
            return service.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public User create(@RequestHeader HttpHeaders headers, @Valid @RequestBody UserDto userDto) {
        try {
            User user = modelConversionHelper.userFromDto(userDto);
            service.create(user);
            return user;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public User update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody UserDto userDto) {
        try {
            User user = modelConversionHelper.userFromDto(userDto);
            service.update(user);
            return user;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            Credential credential = authenticationHelper.tryGetUser(headers);
            User credentialUser = authenticationHelper.convertCredentialToUser(credential);
            service.delete(id, credentialUser);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/filter/customers")
    public List<PersonalInfo> filterCustomers(@RequestHeader HttpHeaders headers,
                                              @RequestParam(required = false) Optional<String> firstName,
                                              @RequestParam(required = false) Optional<String> lastName,
                                              @RequestParam(required = false) Optional<String> email,
                                              @RequestParam(required = false) Optional<String> phoneNumber) {
        try {
            Credential credential = authenticationHelper.tryGetUser(headers);
            User credentialUser = authenticationHelper.convertCredentialToUser(credential);
            return service.filterCustomers(firstName, lastName, email, phoneNumber, credentialUser);
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


}

