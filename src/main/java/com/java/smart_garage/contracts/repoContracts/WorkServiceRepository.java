package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.WorkService;

import java.util.List;
import java.util.Optional;

public interface WorkServiceRepository {

    List<WorkService> getAllWorkServices();

    WorkService getById(int id);

    WorkService getByName(String name);

    WorkService create(WorkService service);

    WorkService update(WorkService service);

    void delete(int id);

    List<WorkService> filterWorkServicesByNameAndPrice(Optional<String> name,
                                                       Optional<Double> price);
}
