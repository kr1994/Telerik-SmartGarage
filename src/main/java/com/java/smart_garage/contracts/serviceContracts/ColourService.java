package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Colour;
import com.java.smart_garage.models.Credential;

import java.util.List;

public interface ColourService {
    List<Colour> getAllColours();

    Colour getById(int id);

    Colour getByName(String name);

    void create(Colour colour, Credential credential);

    void delete(int id, Credential credential);
}
