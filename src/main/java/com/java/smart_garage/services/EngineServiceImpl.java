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
    public void create(Engine engine, User credentialUser) {
        boolean duplicateExists = true;

        if (!(credentialUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create new engine.");
        }

        try {
            repository.getById(engine.getEngineId());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if(engineCheck(engine)){
            throw new DuplicateEntityException("Engine", "hpw", engine.getHpw(), "fuel", engine.getFuel().getFuelName(), "cc", engine.getCubicCapacity());
        }

        if (duplicateExists) {
            throw new DuplicateEntityException("Engine", "id", engine.getEngineId());
        }

        repository.create(engine);
    }

    @Override
    public void delete(int id, User credentialUser) {
        if (!(credentialUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can delete an engine.");
        }
        repository.delete(id);
    }

    private boolean engineCheck(Engine value){
        List<Engine> engines = getAllEngines();

        for (Engine engine : engines) {
            if(value.getHpw()==engine.getHpw() &&
                    value.getFuel().getFuelName().equals(engine.getFuel().getFuelName()) &&
                            value.getCubicCapacity()==value.getCubicCapacity()){
                return true;
            }

        }return false;
    }
}

