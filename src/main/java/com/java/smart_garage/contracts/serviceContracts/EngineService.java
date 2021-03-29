package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Engine;
import com.java.smart_garage.models.User;

import java.util.List;

public interface EngineService {

    List<Engine> getAllEngines();

    Engine getById(int id);

    void create(Engine engine, User user);

    void delete(int id, User user);

}
