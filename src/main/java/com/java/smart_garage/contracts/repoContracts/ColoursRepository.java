package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.Colour;

import java.util.List;

public interface ColoursRepository {
    List<Colour> getAllColours();

    Colour getById(int id);

    Colour getByName(String name);

    Colour create(Colour colour);

    void delete(int id);
}
