package com.java.smart_garage.controllers.rest;

import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.contracts.serviceContracts.PersonalInfoService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.PersonalInfo;
import com.java.smart_garage.models.Credential;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.dto.PersonalInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("smartgarage/ps")
public class PersonalInfoController {

    private final PersonalInfoService service;
    private final ModelConversionHelper modelConversionHelper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public PersonalInfoController(PersonalInfoService service,
                                  ModelConversionHelper modelConversionHelper,
                                  AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.modelConversionHelper = modelConversionHelper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<PersonalInfo> getAllPersonalInformations(){
        return service.getAllPersonalInformations();
    }

    @GetMapping("/{id}")
    public PersonalInfo getById(@PathVariable int id) {
        try {
            return service.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/first_name")
    public PersonalInfo getByFirstName(@RequestParam String firstName) {
        try {
            return service.getByFirstName(firstName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/last_name")
    public PersonalInfo getByLastName(@RequestParam String lastName) {
        try {
            return service.getByLastName(lastName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/email")
    public PersonalInfo getByEmail(@RequestParam String email) {
        try {
            return service.getByEmail(email);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public PersonalInfo create(@RequestHeader HttpHeaders headers, @Valid @RequestBody PersonalInfoDto personalInfoDto) {
        try {
            Credential credential = authenticationHelper.tryGetUser(headers);
            User user = authenticationHelper.convertCredentialToUser(credential);
            PersonalInfo personalInfo = modelConversionHelper.personalInfoFromDto(personalInfoDto);
            service.create(personalInfo, user);
            return personalInfo;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public PersonalInfo update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody PersonalInfoDto personalInfoDto) {
        try {
            Credential credential = authenticationHelper.tryGetUser(headers);
            User user = authenticationHelper.convertCredentialToUser(credential);
            PersonalInfo personalInfo = modelConversionHelper.personalInfoFromDto(personalInfoDto);  //Should be found by id
            service.update(personalInfo, user);
            return personalInfo;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

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

