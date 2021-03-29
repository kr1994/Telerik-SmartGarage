package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.CubicCapacity;

import java.util.List;

public interface CubicCapacityRepository {

    List<CubicCapacity> getAllCubicCapacities();

    CubicCapacity getById(int id);

    CubicCapacity create(CubicCapacity cubicCapacity);

    void delete(int id);
}
