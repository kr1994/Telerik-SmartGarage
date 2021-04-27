package com.java.smart_garage.services;

import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.contracts.repoContracts.CarServiceRepository;
import com.java.smart_garage.contracts.serviceContracts.CarServiceService;
import com.java.smart_garage.contracts.serviceContracts.CurrencyMultiplierService;
import com.java.smart_garage.exceptions.DateInPastException;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.CarService;
import com.java.smart_garage.models.Invoice;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.viewDto.WorkServiceView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.java.smart_garage.models.ModelsConstants.ModelsConstants.BASE_CURRENCY;

@Service
public class CarServiceServiceImpl implements CarServiceService {

    public static final String BASE_CURRENCY_NAME = "EUR";
    private final CarServiceRepository repository;
    private final CurrencyMultiplierService multiplierService;
    private final ModelConversionHelper conversionHelper;

    @Autowired
    public CarServiceServiceImpl(CarServiceRepository repository, CurrencyMultiplierService multiplierService, ModelConversionHelper conversionHelper) {
        this.repository = repository;
        this.multiplierService = multiplierService;
        this.conversionHelper = conversionHelper;
    }

    @Override
    public List<CarService> getAllCarServices(){
        return repository.getAllCarServices();
    }

    @Override
    public List<WorkServiceView> getAllCarServicesByView(Optional<Date> startingDate, Optional<Date> endingDate,int id,Optional<String> currency){


        List<WorkServiceView> workServiceView= new ArrayList<>();
        if(currency.isEmpty() || currency.get().equals(BASE_CURRENCY_NAME)){
            List<CarService> carServices = repository.filterByDateAndCarId(startingDate,endingDate,id);
            try {
                workServiceView = listWorkServices(carServices, BASE_CURRENCY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            String valueToChange = currency.toString().substring(9,12);

            List<CarService> carServices = repository.filterByDateAndCarId(startingDate,endingDate,id);
            try {
                double multiplier = multiplierService.getCurrency(valueToChange);
                workServiceView = listWorkServices(carServices, multiplier);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }return workServiceView;
    }

    @Override
    public CarService getById(int id){
        return repository.getById(id);
    }

    @Override
    public List<CarService> getAllCarServicesByCustomer(int id) {return repository.getAllCarServicesByCustomer(id);}

    @Override
    public List<CarService> getAllCarServicesByCar(int id) {return repository.getAllCarServicesByCar(id);}

    @Override
    public double getCarServicesPrice(int id) {
        double price = 0;
        List<CarService> carServices = repository.getCarServicesPrice(id);
        for (CarService carService : carServices) {
            price = price + carService.getService().getWorkServicePrice();
        }
        return price;
    }


    @Override
    public void create(CarService carService, User user) {
        boolean duplicateExists = true;

        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create a new car service.");
        }

        try {
            repository.getById(carService.getCarServicesId());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Car Service", "id", carService.getCarServicesId());
        }

        repository.create(carService);
    }

    @Override
    public void create(CarService carService, Invoice invoice, User user) {
        boolean duplicateExists = true;

        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create a new car service.");
        }

        try {
            repository.getById(carService.getCarServicesId());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Car Service", "id", carService.getCarServicesId());
        }
        if(invoice.getDate().before(Date.valueOf(LocalDate.now()))){
            throw new DateInPastException(invoice.getDate());
        }

        repository.create(carService,invoice);
    }

    @Override
    public void delete(int id, User user) {
        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee  can delete a car.");
        }
        CarService carService = getById(id);
        if(carService.getInvoice().getDate().before(Date.valueOf(LocalDate.now()))){
            throw new DateInPastException(carService.getInvoice().getDate());
        }
        repository.delete(id);
    }

    private List<WorkServiceView> listWorkServices(List<CarService> carServices, double currency){
        List<WorkServiceView> workService = new ArrayList<>();
        for (CarService carService : carServices) {
            workService.add(conversionHelper.objectToViewWork(carService,currency));
        }
        return workService;
    }


}



