package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.Manufacturer;

import java.util.List;

public interface ManufacturerRepository {
    List<Manufacturer> getAllManufacturers();

    Manufacturer getById(int id);

    Manufacturer getByName(String name);

    Manufacturer create(Manufacturer manufacturer);

    void delete(int id);
}
