package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.HorsePower;
import com.java.smart_garage.models.User;

import java.util.List;

public interface HorsePowerService {

    List<HorsePower> getAllCubicCapacities();

    HorsePower getById(int id);

    void create(HorsePower cubicCapacity, User employeeUser);

    void delete(int id, User employeeUser);
}
