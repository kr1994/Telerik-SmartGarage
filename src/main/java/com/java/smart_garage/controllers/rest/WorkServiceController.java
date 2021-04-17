package com.java.smart_garage.controllers.rest;

import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.contracts.serviceContracts.WorkServiceService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.Credential;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.WorkService;
import com.java.smart_garage.models.dto.WorkServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("smartgarage/workservices")
public class WorkServiceController {

    private final WorkServiceService service;
    private final ModelConversionHelper modelConversionHelper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public WorkServiceController(WorkServiceService service,
                                 ModelConversionHelper modelConversionHelper,
                                 AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.modelConversionHelper = modelConversionHelper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping("/")
    public List<WorkService> getAllWorkServices( @RequestParam Optional<String> currency){
        return service.getAllWorkServices(currency);
    }

    @GetMapping("/{id}")
    public WorkService getById(@RequestParam Optional<String> currency,@PathVariable int id) {
        try {
            return service.getById(currency,id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/name")
    public WorkService getByName(@RequestParam String name) {
        try {
            return service.getByName(name);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public WorkService create(@RequestHeader HttpHeaders headers, @Valid @RequestBody WorkServiceDto workServiceDto) {
        try {
            Credential credential = authenticationHelper.tryGetUser(headers);
            User user = authenticationHelper.convertCredentialToUser(credential);
            WorkService workService = modelConversionHelper.workServiceFromDto(workServiceDto);
            service.create(workService, user);
            return workService;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public WorkService update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody WorkServiceDto workServiceDto) {
        try {
            Credential credential = authenticationHelper.tryGetUser(headers);
            User user = authenticationHelper.convertCredentialToUser(credential);
            WorkService workService = modelConversionHelper.workServiceFromDto(workServiceDto);  //Should be found by id
            service.update(workService, user);
            return workService;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
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

