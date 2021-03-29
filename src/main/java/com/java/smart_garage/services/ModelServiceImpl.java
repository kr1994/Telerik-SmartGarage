package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.ModelRepository;
import com.java.smart_garage.contracts.serviceContracts.ModelService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Model;
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
    public List<Model> getAllModels(){
        return repository.getAllModels();
    }

    @Override
    public Model getModelById(int id){
        return repository.getById(id);
    }

    @Override
    public Model getModelByName(String name){
        return repository.getByName(name);
    }

    @Override
    public void create(Model model, User user) {

        boolean duplicateExists = true;

        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException("Only employee can create models.");
        }
        try {
            repository.getByName(model.getModelName());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new DuplicateEntityException("Model", "name", model.getModelName());
        }

        repository.create(model);
    }


    @Override
    public void delete(int id, User user) {

        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException("Only employee can delete cities.");
        }
        Model model = new Model();
        try {
            model = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Model", "id", id);
        }
        repository.delete(id);
    }
}
