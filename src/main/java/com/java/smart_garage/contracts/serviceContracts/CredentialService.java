package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Credential;
import com.java.smart_garage.models.User;

import java.util.List;

public interface CredentialService {

    List<Credential> getAllCredentials();

    Credential getById(int id);

    Credential getByUsername(String username);

    void create(Credential credential, User employeeUser);

    void update(Credential credential, User employeeUser);

    void delete(int id, User employeeUser);
}
