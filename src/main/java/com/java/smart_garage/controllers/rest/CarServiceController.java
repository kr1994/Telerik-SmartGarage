package com.java.smart_garage.controllers.rest;

import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.contracts.serviceContracts.CarServiceService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.CarService;
import com.java.smart_garage.models.Credential;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.dto.CarServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("smartgarage/service")
public class CarServiceController {

    private final CarServiceService service;
    private final ModelConversionHelper modelConversionHelper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public CarServiceController(CarServiceService service,
                                ModelConversionHelper modelConversionHelper,
                                AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.modelConversionHelper = modelConversionHelper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<CarService> getAllServices(){
        return service.getAllCarServices();
    }

    @GetMapping("/{id}")
    public CarService getByCarService(@PathVariable int id) {
        try {
            return service.getById(id);
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/customer/{id}")
    public List<CarService> getByCarCustomer(@PathVariable int id) {
        try {
            return service.getAllCarServicesByCustomer(id);
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/car/{id}")
    public List<CarService> getAllServicesForCar(@PathVariable int id) {
        try {
            return service.getAllCarServicesByCar(id);
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/car/price/{id}")
    public double getCarServicesPrice(@PathVariable int id) {
        try {
            return service.getCarServicesPrice(id);
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    @PostMapping
    public CarService create(@RequestHeader HttpHeaders headers, @Valid @RequestBody CarServiceDto carServiceDto) {
        try {
            Credential credential = authenticationHelper.tryGetUser(headers);
            User user = authenticationHelper.convertCredentialToUser(credential);
            CarService carService = modelConversionHelper.carServiceFromDto(carServiceDto);
            service.create(carService, user);
            return carService;
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
