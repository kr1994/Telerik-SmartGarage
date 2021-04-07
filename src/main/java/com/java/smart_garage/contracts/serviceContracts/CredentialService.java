package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Credential;

import java.util.List;

public interface CredentialService {

    List<Credential> getAllCredentials();

    Credential getById(int id);

    Credential getByUsername(String username);

    void create(Credential credential, Credential employeeCredential);

    void update(Credential credential, Credential employeeCredential);

    void delete(int id, Credential credential);
}
