package com.java.smart_garage.contracts.serviceContracts;

import java.util.List;

public interface RegistrationPlateService {

    List<RegistrationPlate> getAllRegisrationPlates();

    RegistrationPlate getById(int id);

    RegistrationPlate getByName(String name);

    void create(RegistrationPlate registrationPlate, User user);

    void delete(int id, RegistrationPlate registrationPlate);
}
