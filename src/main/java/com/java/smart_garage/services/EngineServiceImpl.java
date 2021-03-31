package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.EngineRepository;
import com.java.smart_garage.contracts.serviceContracts.EngineService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Engine;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EngineServiceImpl implements EngineService {

    private final EngineRepository repository;

    @Autowired
    public EngineServiceImpl(EngineRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Engine> getAllEngines(){
        return repository.getAllEngines();
    }

    @Override
    public Engine getById(int id){
        return repository.getById(id);
    }

    @Override
    public void create(Engine engine, User user) {
        boolean duplicateExists = true;

        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create new engine.");
        }

        try {
            repository.getById(engine.getEngineId());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Engine", "id", engine.getEngineId());
        }

        repository.create(engine);
    }

    @Override
    public void delete(int id, User user) {
        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can delete an engine.");
        }
        Engine engine = new Engine();
        try {
            engine = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Engine", "id", id);
        }
        repository.delete(id);
    }
}
