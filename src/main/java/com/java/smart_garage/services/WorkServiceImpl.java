package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.ServiceRepository;
import com.java.smart_garage.contracts.serviceContracts.ServiceService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.WorkService;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkServiceImpl implements ServiceService {

    private final ServiceRepository repository;

    @Autowired
    public WorkServiceImpl(ServiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<WorkService> getAllServices(){
        return repository.getAllServices();
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
    public void create(WorkService service, User user) {
        boolean duplicateExists = true;

        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create new service.");
        }

        try {
            repository.getByName(service.getServiceName());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Service", "name", service.getServiceName());
        }

        repository.create(service);
    }

    @Override
    public void delete(int id, User user) {
        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can delete service.");
        }
        WorkService service = new WorkService();
        try {
            service = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Service", "id", id);
        }
        repository.delete(id);
    }
}



