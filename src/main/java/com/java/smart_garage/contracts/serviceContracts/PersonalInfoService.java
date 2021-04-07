package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.User;
import com.java.smart_garage.models.PersonalInfo;

import java.util.List;

public interface PersonalInfoService {

    List<PersonalInfo> getAllPersonalInformations();

    PersonalInfo getById(int id);

    PersonalInfo getByFirstName(String firstName);

    PersonalInfo getByLastName(String lastName);

    PersonalInfo getByEmail(String email);

    void create(PersonalInfo service, User credentialUser);

    void update(PersonalInfo service, User credentialUser);

    void delete(int id, User credentialUser);
}
