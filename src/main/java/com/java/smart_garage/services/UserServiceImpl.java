package com.java.smart_garage.services;

import com.java.smart_garage.configuration.Md5Hashing;
import com.java.smart_garage.contracts.repoContracts.CredentialRepository;
import com.java.smart_garage.contracts.repoContracts.UserRepository;
import com.java.smart_garage.contracts.serviceContracts.EmailService;
import com.java.smart_garage.contracts.serviceContracts.UserService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.CarService;
import com.java.smart_garage.models.Credential;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.viewDto.CustomerViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final CredentialRepository credentialRepository;
    private final EmailService emailService;


    @Autowired
    public UserServiceImpl(UserRepository repository,
                           CredentialRepository credentialRepository,
                           EmailService emailService) {
        this.repository = repository;
        this.credentialRepository = credentialRepository;
        this.emailService = emailService;
    }

    @Override
    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    @Override
    public User getById(int id) {
        return repository.getById(id);
    }

    @Override
    public User getByUserName(String userName) {
        return repository.getByUserName(userName);
    }

    @Override
    public void create(User user, User credentialUser) {
        boolean duplicateExists = true;

        try {
            repository.getById(user.getUserId());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("User", "id", user.getUserId());
        }

        repository.create(user);
    }

    @Override
    public void update(User user, User credentialUser) {

        if (!(credentialUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee or the user can update user.");
        }

        try {
            repository.getById(user.getUserId());
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("User", "id", user.getUserId());
        }

        repository.update(user, user.getCredential(), user.getPersonalInfo(), user.getUserType());
    }

    @Override
    public void delete(int id, User userCredential) {
        if (!(userCredential.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can delete user.");
        }
        repository.delete(id);
    }

    @Override
    public List<CarService> filterCustomers(Optional<String> firstName,
                                            Optional<String> lastName,
                                            Optional<String> email,
                                            Optional<String> phoneNumber,
                                            Optional<String> model,
                                            Optional<Date> dateFrom,
                                            Optional<Date> dateTo,
                                            User userCredential) {

        if (!(userCredential.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can filter customers.");
        }
        return repository.filterCustomers(firstName, lastName, email, phoneNumber, model, dateFrom, dateTo);
    }

    @Override
    public List<CustomerViewDto> sortCustomersByName(List<CustomerViewDto> input, boolean ascending, User userCredential) {
        if (!(userCredential.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can sort customers.");
        }
        return repository.sortCustomersByName(input, ascending);
    }

    @Override
    public List<CustomerViewDto> sortCustomersByVisits(List<CustomerViewDto> input, boolean ascending, User userCredential) {
        if (!(userCredential.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can sort customers.");
        }
        return repository.sortCustomersByVisits(input, ascending);
    }

    @Override
    public int getUserCount() {
        return repository.getAllUsers().size();
    }

    @Override
    public void resetPassword(String email) {
        User user = Optional.ofNullable(repository.getByEmail(email)).orElseThrow(
                () -> new EntityNotFoundException("User", "email"));

        Credential credential = credentialRepository.getById(user.getCredential().getCredentialId());
        String newPassword = Md5Hashing.generateNewPassword(8);
        credential.setPassword(Md5Hashing.md5(newPassword));
        credentialRepository.update(credential);
        emailService.sendMailForNewPassword(email, newPassword);
    }


}



