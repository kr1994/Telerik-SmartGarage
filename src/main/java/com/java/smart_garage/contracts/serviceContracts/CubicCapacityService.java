package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.CubicCapacity;
import com.java.smart_garage.models.User;

import java.util.List;

public interface CubicCapacityService {

    List<CubicCapacity> getAllCubicCapacities();

    CubicCapacity getById(int id);

    void create(CubicCapacity cubicCapacity, User employeeUser);

    void delete(int id, User employeeUser);
}
