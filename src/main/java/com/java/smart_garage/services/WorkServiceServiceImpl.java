package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.WorkServiceRepository;
import com.java.smart_garage.contracts.serviceContracts.CurrencyMultiplierService;
import com.java.smart_garage.contracts.serviceContracts.WorkServiceService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkServiceServiceImpl implements WorkServiceService {

    private final WorkServiceRepository repository;
    private final CurrencyMultiplierService multiplierService;

    @Autowired
    public WorkServiceServiceImpl(WorkServiceRepository repository, CurrencyMultiplierService multiplierService) {
        this.repository = repository;
        this.multiplierService = multiplierService;
    }

    @Override
    public List<WorkService> getAllWorkServices(Optional<String> currency){
        List<WorkService> services = repository.getAllWorkServices();

        if(currency.isEmpty()){
            return repository.getAllWorkServices();
        }

        for (WorkService service : services) {
            service.setWorkServicePrice(Math.round(service.getWorkServicePrice()*multiplierService.getCurrency(currency.get())));
        }
        return services;
    }

    @Override
    public WorkService getById(Optional<String> currency,int id){
        WorkService workService = repository.getById(id);
        if(currency.isEmpty()){
            return workService;
        }

            workService.setWorkServicePrice(Math.round(workService.getWorkServicePrice()*multiplierService.getCurrency(currency.get())));

        return workService;
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
        repository.delete(id);
    }

    @Override
    public List<WorkService> filterWorkServicesByNameAndPrice(Optional<String> name,
                                                              Optional<Double> price,
                                                              User credentialUser) {
        if (!(credentialUser.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can filter work services.");
        }
        return repository.filterWorkServicesByNameAndPrice(name, price);
    }
}



