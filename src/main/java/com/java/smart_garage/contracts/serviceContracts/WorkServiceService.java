package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Credential;
import com.java.smart_garage.models.WorkService;

import java.util.List;

public interface WorkServiceService {

    List<WorkService> getAllWorkServices();

    WorkService getById(int id);

    WorkService getByName(String name);

    void create(WorkService service, Credential credential);

    void update(WorkService service, Credential credential);

    void delete(int id, Credential credential);
}
