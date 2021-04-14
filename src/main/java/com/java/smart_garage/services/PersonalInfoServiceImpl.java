package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.PersonalInfoRepository;
import com.java.smart_garage.contracts.serviceContracts.PersonalInfoService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.IncorrectPhoneException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.PersonalInfo;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.java.smart_garage.models.ModelsConstants.ModelsConstants.PHONE_CODE;

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
    public List<PersonalInfo> getByFirstName(String firstName){
        return repository.getByFirstName(firstName);
    }

    @Override
    public List<PersonalInfo> getByLastName(String lastName){
        return repository.getByLastName(lastName);
    }

    @Override
    public PersonalInfo getByEmail(String email){
        return repository.getByEmail(email);
    }

    @Override
    public void create(PersonalInfo personalInfo, User credentialUser) {
        boolean duplicateExists = true;

        if (!(credentialUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create the personal information!");
        }
        try {
            repository.getByEmail(personalInfo.getEmail());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Personal Information", "name", personalInfo.getEmail());
        }

        if(!truePhoneNumber(credentialUser.getPersonalInfo().getPhoneNumber())){
            throw new IncorrectPhoneException(credentialUser.getPersonalInfo().getPhoneNumber());
        }

        repository.create(personalInfo);
    }

    @Override
    public void update(PersonalInfo personalInfo, User credentialUser) {

        if (!(credentialUser.isEmployee())) {
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
    public void delete(int id, User credentialUser) {
        if (!(credentialUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can delete personal information.");
        }
        repository.delete(id);
    }

    public boolean truePhoneNumber(String number) {
        boolean flag = false;

        String phoneNumber = number.substring(0, 2);

        if (phoneNumber.equals(PHONE_CODE)){
            flag = true;
        }
        return flag;
    }
}
