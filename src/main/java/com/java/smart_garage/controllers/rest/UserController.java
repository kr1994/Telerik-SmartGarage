package com.java.smart_garage.controllers.rest;

import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.contracts.serviceContracts.UserService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.*;
import com.java.smart_garage.models.dto.UserDto;
import com.java.smart_garage.models.viewDto.CustomerViewDto;
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
            User credentialUser = authenticationHelper.tryGetUser(headers);
            service.create(user, credentialUser);
            return user;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public User update(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody UserDto userDto) {
        try {
            User user = modelConversionHelper.userFromDto(userDto);
            User credentialUser = authenticationHelper.tryGetUser(headers);
            service.update(user, credentialUser);
            return user;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User credentialUser = authenticationHelper.tryGetUser(headers);
            service.delete(id, credentialUser);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/filter/customers")
    public List<CustomerViewDto> filterCustomers(@RequestHeader HttpHeaders headers,
                                                 @RequestParam(required = false) Optional<String> firstName,
                                                 @RequestParam(required = false) Optional<String> lastName,
                                                 @RequestParam(required = false) Optional<String> email,
                                                 @RequestParam(required = false) Optional<String> phoneNumber,
                                                 @RequestParam(required = false) Optional<String> model,
                                                 @RequestParam(required = false) Optional<Date> dateFrom,
                                                 @RequestParam(required = false) Optional<Date> dateTo) {
        try {
            User credentialUser = authenticationHelper.tryGetUser(headers);
            List<CarService> queryResult = service.filterCustomers(firstName, lastName, email, phoneNumber,
                    model, dateFrom, dateTo, credentialUser);
            return mapCSToCustomerView(queryResult);
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/sort/customers/name")
    public List<CustomerViewDto> sortCustomersByName(@RequestHeader HttpHeaders headers,
                                                     @RequestParam(required = true) boolean ascending) {
        try {
            User credentialUser = authenticationHelper.tryGetUser(headers);
            List<CustomerViewDto> allCustomers = filterCustomers(headers, Optional.empty(), Optional.empty(), Optional.empty(),
                    Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
            return service.sortCustomersByName(allCustomers, ascending, credentialUser);
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/sort/customers/visits")
    public List<CustomerViewDto> sortCustomersByVisits(@RequestHeader HttpHeaders headers,
                                                       @RequestParam(required = true) boolean ascending) {
        try {
            User credentialUser = authenticationHelper.tryGetUser(headers);
            List<CustomerViewDto> allCustomers = filterCustomers(headers, Optional.empty(), Optional.empty(), Optional.empty(),
                    Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
            return service.sortCustomersByVisits(allCustomers, ascending, credentialUser);
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private List<CustomerViewDto> mapCSToCustomerView(List<CarService> queryResult) {

        List<CustomerViewDto> resultQuery = new ArrayList<CustomerViewDto>();
        List<CustomerViewDto> result = new ArrayList<CustomerViewDto>();
        List<Integer> indexesForDeletion = new ArrayList<Integer>();
        List<Date> currentVisits = new ArrayList<Date>();

        for (CarService cs : queryResult) {  // map CarService object to return view
            CustomerViewDto cvd = new CustomerViewDto();
            List<Date> visits = new ArrayList<Date>();
            cvd.setFirstName(cs.getCar().getOwner().getPersonalInfo().getFirstName());
            cvd.setLastName(cs.getCar().getOwner().getPersonalInfo().getLastName());
            cvd.setEmail(cs.getCar().getOwner().getPersonalInfo().getEmail());
            cvd.setPhoneNumber(cs.getCar().getOwner().getPersonalInfo().getPhoneNumber());
            cvd.setUserType(cs.getCar().getOwner().getUserType());
            cvd.setCarModel(cs.getCar().getModel().getModel());
            visits.add(cs.getInvoice().getDate());
            cvd.setVisitsInRange(visits);
            resultQuery.add(cvd);
        }

        int resultTempSize = resultQuery.size();
        int duplicateCounter = 0;

        if (resultTempSize > 1) {
            for (int i = 0; i < resultTempSize - 1; i++) {            // extract duplicating dates to a single object
                CustomerViewDto cvd = resultQuery.get(i);
                CustomerViewDto cvdNext = resultQuery.get(i + 1);
                if (duplicateCounter == 0) {
                    currentVisits = cvd.getVisitsInRange();
                }
                if (cvd.getEmail().equals(cvdNext.getEmail())) {   // compare if the list contain duplicated emails -> in that case they are equal
                    duplicateCounter++;
                    indexesForDeletion.add(i + 1);
                    Date moveDate = cvdNext.getVisitsInRange().get(0);
                    currentVisits.add(moveDate);
                } else {
                    duplicateCounter = 0;
                }
                resultQuery.get(i).setVisitsInRange(currentVisits);  //move date to previous object
            }
        }

        for (int k = 0, l = 0; k < resultQuery.size(); k++) {   //delete duplicated parts
            if (k == indexesForDeletion.get(l)) {
                l++;
                if (l == indexesForDeletion.size()) {
                    l = 0;
                }
            } else {
                result.add(resultQuery.get(k));
            }
        }

        return result;
    }
}

