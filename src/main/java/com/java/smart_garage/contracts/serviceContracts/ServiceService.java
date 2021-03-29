package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Service;
import com.java.smart_garage.models.User;

import java.util.List;

public interface ServiceService {

    List<Service> getAllServices();

    Service getById(int id);

    Service getByName(String name);

    void create(Service service, User user);

    void delete(int id, User user);
}
