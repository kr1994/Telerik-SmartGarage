package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.IdentificationRepository;
import com.java.smart_garage.contracts.serviceContracts.IdentificationService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Identification;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdentificationServiceImpl implements IdentificationService {

    private final IdentificationRepository repository;

    @Autowired
    public IdentificationServiceImpl(IdentificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Identification> getAllIdentifications(){
        return repository.getAllIdentifications();
    }

    @Override
    public Identification getById(int id){
        return repository.getById(id);
    }

    @Override
    public Identification getByName(String name){
        return repository.getByName(name);
    }

    @Override
    public void create(Identification identification, User user) {
        boolean duplicateExists = true;

        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create new identification.");
        }

        try {
            repository.getByName(identification.getIdentification());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Identification", "name", identification.getIdentification());
        }

        repository.create(identification);
    }

    @Override
    public void delete(int id, User user) {
        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can delete identification.");
        }
        Identification identification = new Identification();
        try {
            identification = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Identification", "id", id);
        }
        repository.delete(id);
    }
}


