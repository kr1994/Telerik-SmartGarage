package com.java.smart_garage.services;

import com.java.smart_garage.models.Manufacturer;
import com.java.smart_garage.models.User;

import java.util.List;

public interface ManufactureService {
    List<Manufacturer> getAllManufacturers();

    Manufacturer getById(int id);

    Manufacturer getByName(String name);

    void create(Manufacturer manufacturer, User employeeUser);

    void delete(int id, User employeeUser);
}
