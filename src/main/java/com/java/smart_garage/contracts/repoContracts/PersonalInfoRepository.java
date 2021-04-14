package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.PersonalInfo;

import java.util.List;

public interface PersonalInfoRepository {

    List<PersonalInfo> getAllPersonalInformations();

    PersonalInfo getById(int id);

    List<PersonalInfo> getByFirstName(String firstName);

    List<PersonalInfo> getByLastName(String firstName);

    PersonalInfo getByEmail(String email);

    PersonalInfo create(PersonalInfo personalInfo);

    PersonalInfo update(PersonalInfo personalInfo);

    void delete(int id);
}
