package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.HorsePower;

import java.util.List;

public interface HorsePowerRepository {

    List<HorsePower> getAllHorsePowers();

    HorsePower getById(int id);

    HorsePower getByPower(int power);

    HorsePower create(HorsePower horsePower);

    void delete(int id);

}
