package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Credential;
import com.java.smart_garage.models.PersonalInfo;

import java.util.List;

public interface PersonalInfoService {

    List<PersonalInfo> getAllPersonalInformations();

    PersonalInfo getById(int id);

    PersonalInfo getByFirstName(String firstName);

    PersonalInfo getByLastName(String lastName);

    PersonalInfo getByEmail(String email);

    void create(PersonalInfo service, Credential credential);

    void update(PersonalInfo service, Credential credential);

    void delete(int id, Credential credential);
}
