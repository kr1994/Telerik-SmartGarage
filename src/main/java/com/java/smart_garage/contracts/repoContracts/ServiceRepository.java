package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.Service;

import java.util.List;

public interface ServiceRepository {

    List<Service> getAll();

    Service getById(int id);

    Service getByName(String name);

    Service create(Service service);

    void delete(int id);
}
