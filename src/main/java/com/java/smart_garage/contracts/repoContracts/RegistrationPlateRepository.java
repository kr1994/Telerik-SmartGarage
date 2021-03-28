package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.RegistrationPlate;

import java.util.List;

public interface RegistrationPlateRepository {

    List<RegistrationPlate> getAll();

    RegistrationPlate getById(int id);

    RegistrationPlate getByName(String name);

    RegistrationPlate create(RegistrationPlate registrationPlate);

    void delete(int id);
}
