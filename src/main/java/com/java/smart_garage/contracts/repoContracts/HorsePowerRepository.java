package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.HorsePower;

import java.util.List;

public interface HorsePowerRepository {

    List<HorsePower> getAll();

    HorsePower getById(int id);

    //HorsePower getByName(String name);

    HorsePower create(HorsePower horsePower);

    void delete(int id);

}
