package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.WorkService;

import java.util.List;

public interface ServiceRepository {

    List<WorkService> getAllServices();

    WorkService getById(int id);

    WorkService getByName(String name);

    WorkService create(WorkService service);

    void delete(int id);
}
