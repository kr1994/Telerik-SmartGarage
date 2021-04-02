package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.User;
import com.java.smart_garage.models.WorkService;

import java.util.List;

public interface WorkServiceService {

    List<WorkService> getAllWorkServices();

    WorkService getById(int id);

    WorkService getByName(String name);

    void create(WorkService service, User user);

    void update(WorkService service, User user);

    void delete(int id, User user);
}