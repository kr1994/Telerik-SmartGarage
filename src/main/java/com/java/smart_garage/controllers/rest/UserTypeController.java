package com.java.smart_garage.controllers.rest;

import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.contracts.serviceContracts.UserTypeService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.Credential;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.UserType;
import com.java.smart_garage.models.dto.UserTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("smartgarage/types")
public class UserTypeController {

    private final UserTypeService service;
    private final ModelConversionHelper modelConversionHelper;
    private final AuthenticationHelper authenticationHelper;


    @Autowired
    public UserTypeController(UserTypeService service,
                              ModelConversionHelper modelConversionHelper,
                              AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.modelConversionHelper = modelConversionHelper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping()
    public List<UserType> getAllUsers(){
        return service.getAllTypes();
    }

    @PostMapping
    public UserType create(@RequestHeader HttpHeaders headers, @Valid @RequestBody UserTypeDto userTypeDto) {
        try {
            Credential credential = authenticationHelper.tryGetUser(headers);
            User user = authenticationHelper.convertCredentialToUser(credential);
            UserType userType = modelConversionHelper.userTypeFromDto(userTypeDto);
            service.create(userType, user);
            return userType;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            Credential credential = authenticationHelper.tryGetUser(headers);
            User user = authenticationHelper.convertCredentialToUser(credential);
            service.delete(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
