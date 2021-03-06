package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.*;

import java.util.List;

public interface EngineRepository {

    List<Engine> getAllEngines();

    Engine getById(int id);

    Engine create(Engine engine);

    Engine update(Engine engine,
                  int horsePower,
                  Fuel fuel,
                  int cubicCapacity);

    void delete(int id);
}
