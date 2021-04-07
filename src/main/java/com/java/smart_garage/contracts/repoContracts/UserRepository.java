package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.Credential;

import java.util.List;

public interface UserRepository {

    List<Credential> getAllUsers();

    Credential getById(int id);

    Credential getByUsername(String username);

    Credential create(Credential credential);

    Credential update(Credential credential);

    void delete(int id);
}
