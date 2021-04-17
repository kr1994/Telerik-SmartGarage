package com.java.smart_garage.controllers.rest;

import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.contracts.serviceContracts.AutomobileService;
import com.java.smart_garage.contracts.serviceContracts.CarServiceService;
import com.java.smart_garage.contracts.serviceContracts.InvoiceService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.*;
import com.java.smart_garage.models.dto.CarServiceDto;
import com.java.smart_garage.models.viewDto.CarServiceViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("smartgarage/service")
public class CarServiceController {

    private final CarServiceService service;
    private final AutomobileService automobileService;
    private final ModelConversionHelper modelConversionHelper;
    private final AuthenticationHelper authenticationHelper;
    private final InvoiceService invoiceService;



    @Autowired
    public CarServiceController(CarServiceService service,
                                AutomobileService automobileService, ModelConversionHelper modelConversionHelper,
                                AuthenticationHelper authenticationHelper, InvoiceService invoiceService) {
        this.service = service;
        this.automobileService = automobileService;
        this.modelConversionHelper = modelConversionHelper;
        this.authenticationHelper = authenticationHelper;

        this.invoiceService = invoiceService;
    }

    @GetMapping
    public List<CarServiceViewDto> getAllServices(@RequestParam Optional<Date> startingDate,
                                                  @RequestParam Optional<Date> endingDate,@RequestParam Optional<String> currency){
        List<Automobile> cars = automobileService.getAllCars();

        return getCarServiceViewDto(startingDate, endingDate, currency, cars);
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
    public List<CarServiceViewDto> getByCarCustomer(@RequestParam Optional<Date> startingDate,
                                                    @RequestParam Optional<Date> endingDate,@PathVariable int id,@RequestParam Optional<String> currency) {
        try {
            List<Automobile> cars = automobileService.getAllCarsByOwner(id);

            return getCarServiceViewDto(startingDate, endingDate, currency, cars);
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private List<CarServiceViewDto> getCarServiceViewDto(@RequestParam Optional<Date> startingDate, @RequestParam Optional<Date> endingDate, @RequestParam Optional<String> currency, List<Automobile> cars) {
        List<CarServiceViewDto> carServiceViewDto = new ArrayList<>();

        for (Automobile car : cars) {
            if(!service.getAllCarServicesByView(startingDate,endingDate,car.getId(),currency).isEmpty())
            carServiceViewDto.add(modelConversionHelper.objectToView(car,service.getAllCarServicesByView(startingDate,endingDate,car.getId(),currency)));
        }
        return carServiceViewDto;
    }

    @GetMapping("/car/{id}")
    public CarServiceViewDto getAllServicesForCar(@RequestParam Optional<Date> startingDate,
                                                  @RequestParam Optional<Date> endingDate,@PathVariable int id,@RequestParam Optional<String> currency) {
        try {
            return modelConversionHelper.objectToView(automobileService.getById(id),service.getAllCarServicesByView(startingDate,endingDate,id,currency));
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

           try{
               Invoice invoice = invoiceService.getByDate(carServiceDto.getInvoice().getDate());
               carService.setInvoice(invoice);
               service.create(carService, user);
           }catch (IndexOutOfBoundsException e){
               Invoice invoice = modelConversionHelper.invoiceFromDto(carServiceDto.getInvoice());
          service.create(carService,invoice,user);

           }

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
