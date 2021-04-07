package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Manufacturer;
import com.java.smart_garage.models.Credential;

import java.util.List;

public interface ManufactureService {
    List<Manufacturer> getAllManufacturers();

    Manufacturer getById(int id);

    Manufacturer getByName(String name);

    void create(Manufacturer manufacturer, Credential employeeCredential);

    void delete(int id, Credential employeeCredential);
}
