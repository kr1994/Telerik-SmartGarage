package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.RegistrationPlate;
import com.java.smart_garage.models.User;

import java.util.List;

public interface RegistrationPlateService {

    List<RegistrationPlate> getAllRegistrationPlates();

    RegistrationPlate getById(int id);

    RegistrationPlate getByName(String name);

    void create(RegistrationPlate registrationPlate, User user);

    void delete(int id, User employeeUser);
}
