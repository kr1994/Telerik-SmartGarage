package com.java.smart_garage.controllers.rest;

import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.contracts.serviceContracts.ManufactureService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.Manufacturer;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.dto.ManufacturerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("smartgarage/manufacturer")
public class ManufacturerController {

    private final ManufactureService service;
    private final ModelConversionHelper modelConversionHelper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public ManufacturerController(ManufactureService service,
                                  ModelConversionHelper modelConversionHelper,
                                  AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.modelConversionHelper = modelConversionHelper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Manufacturer> getAllManufacturers(){
        return service.getAllManufacturers();
    }

    @GetMapping("/{id}")
    public Manufacturer getById(@PathVariable int id) {
        try {
            return service.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Manufacturer create(@RequestHeader HttpHeaders headers, @Valid @RequestBody ManufacturerDto manufacturerDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Manufacturer manufacturer = modelConversionHelper.manufacturerFromDto(manufacturerDto);
            service.create(manufacturer, user);
            return manufacturer;
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
