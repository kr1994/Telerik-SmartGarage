package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.*;

import java.util.List;

public interface EngineRepository {

    List<Engine> getAll();

    Engine getById(int id);

    Engine create(Engine engine);

    Engine update(Engine engine,
                  HorsePower horsePower,
                  Fuel fuel,
                  CubicCapacity cubicCapacity);

    void delete(int id);
}
