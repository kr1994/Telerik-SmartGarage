package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.PersonalInfoRepository;
import com.java.smart_garage.contracts.serviceContracts.PersonalInfoService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.PersonalInfo;
import com.java.smart_garage.models.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalInfoServiceImpl implements PersonalInfoService {

    private final PersonalInfoRepository repository;

    @Autowired
    public PersonalInfoServiceImpl(PersonalInfoRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PersonalInfo> getAllPersonalInformations(){
        return repository.getAllPersonalInformations();
    }

    @Override
    public PersonalInfo getById(int id){
        return repository.getById(id);
    }

    @Override
    public PersonalInfo getByFirstName(String firstName){
        return repository.getByFirstName(firstName);
    }

    @Override
    public PersonalInfo getByLastName(String lastName){
        return repository.getByLastName(lastName);
    }

    @Override
    public PersonalInfo getByEmail(String email){
        return repository.getByEmail(email);
    }

    @Override
    public void create(PersonalInfo personalInfo, Credential credential) {
        boolean duplicateExists = true;

        if (!(credential.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create new personal information.");
        }

        try {
            repository.getByFirstName(personalInfo.getFirstName());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Personal Information", "name", personalInfo.getFirstName());
        }

        repository.create(personalInfo);
    }

    @Override
    public void update(PersonalInfo personalInfo, Credential employeeCredential) {

        if (!(employeeCredential.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee or the user can modify the personal information!");
        }
        try {
            repository.getById(personalInfo.getPersonalId());
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Personal Information", "id", personalInfo.getPersonalId());
        }

        repository.update(personalInfo);
    }

    @Override
    public void delete(int id, Credential credential) {
        if (!(credential.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can delete personal information.");
        }
        PersonalInfo personalInfo = new PersonalInfo();
        try {
            personalInfo = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Personal Information", "id", id);
        }
        repository.delete(id);
    }
}



