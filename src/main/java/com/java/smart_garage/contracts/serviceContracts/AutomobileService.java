package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Automobile;
import com.java.smart_garage.models.User;

import java.util.List;

public interface AutomobileService {
    List<Automobile> getAllCars();

    Automobile getById(int id);

    List<Automobile> getAllCarsByOwner(int id);

    void create(Automobile automobile, User user);

    void update(Automobile automobile, User user);

    void delete(int id, User user);

    int getCarCount();
}
