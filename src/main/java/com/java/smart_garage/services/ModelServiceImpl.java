package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.ModelRepository;
import com.java.smart_garage.contracts.serviceContracts.ModelService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.ModelCar;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository repository;

    @Autowired
    public ModelServiceImpl(ModelRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ModelCar> getAllModels(){
        return repository.getAllModels();
    }

    @Override
    public ModelCar getModelById(int id){
        return repository.getById(id);
    }

    @Override
    public ModelCar getModelByName(String name){
        return repository.getByName(name);
    }

    @Override
    public void create(ModelCar modelCar, User credentialUser) {

        boolean duplicateExists = true;

        if (!credentialUser.isEmployee()) {
            throw new UnauthorizedOperationException("Only employee can create models.");
        }
        try {
            repository.getByName(modelCar.getModel());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new DuplicateEntityException("Model", "name", modelCar.getModel());
        }

        repository.create(modelCar);
    }

    @Override
    public void delete(int id, User credentialUser) {
        if (!credentialUser.isEmployee()) {
            throw new UnauthorizedOperationException("Only employee can delete model.");
        }
        repository.delete(id);
    }
}
