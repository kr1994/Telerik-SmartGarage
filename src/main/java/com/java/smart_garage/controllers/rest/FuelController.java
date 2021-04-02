package com.java.smart_garage.controllers.rest;


import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.contracts.serviceContracts.FuelService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.Fuel;
import com.java.smart_garage.models.Model;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.dto.FuelDto;
import com.java.smart_garage.models.dto.ModelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("smartgarage/fuels")
public class FuelController {

    private final FuelService service;
    private final ModelConversionHelper modelConversionHelper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public FuelController(FuelService service,
                          ModelConversionHelper modelConversionHelper,
                          AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.modelConversionHelper = modelConversionHelper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Fuel> getAllFuels(){
        return service.getAllFuels();
    }

    @GetMapping("/{id}")
    public Fuel getById(@PathVariable int id) {
        try {
            return service.getById(id);
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Fuel create(@RequestHeader HttpHeaders headers, @Valid @RequestBody FuelDto fuelDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Fuel fuel = modelConversionHelper.fuelFromDto(fuelDto);
            service.create(fuel, user);
            return fuel;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.delete(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
