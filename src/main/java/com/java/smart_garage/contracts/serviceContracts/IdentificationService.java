package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Identification;
import com.java.smart_garage.models.User;

import java.util.List;

public interface IdentificationService {

    List<Identification> getAllIdentifications();

    Identification getById(int id);

    Identification getByName(String name);

    void create(Identification identification, User user);

    void delete(int id, User user);
}
