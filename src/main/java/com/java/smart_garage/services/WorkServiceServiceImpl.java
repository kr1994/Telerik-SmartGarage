package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.WorkServiceRepository;
import com.java.smart_garage.contracts.serviceContracts.WorkServiceService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.WorkService;
import com.java.smart_garage.models.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkServiceServiceImpl implements WorkServiceService {

    private final WorkServiceRepository repository;

    @Autowired
    public WorkServiceServiceImpl(WorkServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<WorkService> getAllWorkServices(){
        return repository.getAllWorkServices();
    }

    @Override
    public WorkService getById(int id){
        return repository.getById(id);
    }

    @Override
    public WorkService getByName(String name){
        return repository.getByName(name);
    }

    @Override
    public void create(WorkService workService, User credentialUser) {
        boolean duplicateExists = true;

        if (!(credentialUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create new work service.");
        }

        try {
            repository.getByName(workService.getWorkServiceName());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Work Service", "name", workService.getWorkServiceName());
        }

        repository.create(workService);
    }

    @Override
    public void update(WorkService workService, User credentialUser) {

        if (!(credentialUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee or the user can modify the work service!");
        }
        try {
            repository.getById(workService.getWorkServiceId());
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Work Service", "id", workService.getWorkServiceId());
        }

        repository.update(workService);
    }

    @Override
    public void delete(int id, User credentialUser) {
        if (!(credentialUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can delete work service.");
        }
        WorkService workService = new WorkService();
        try {
            workService = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Work Service", "id", id);
        }
        repository.delete(id);
    }
}



