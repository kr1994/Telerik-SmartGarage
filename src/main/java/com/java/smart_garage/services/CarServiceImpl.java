package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.CarRepository;
import com.java.smart_garage.contracts.repoContracts.CityRepository;
import com.java.smart_garage.contracts.serviceContracts.CarService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.IncorrectPlateRegistrationException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Car;
import com.java.smart_garage.models.City;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository repository;
    private final CityRepository cityRepository;

    @Autowired
    public CarServiceImpl(CarRepository repository, CityRepository cityRepository) {
        this.repository = repository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<Car> getAllCars() {
        return repository.getAllCars();
    }

    @Override
    public Car getById(int id) {
        return repository.getById(id);
    }

    @Override
    public void create(Car car, User user) {
        boolean duplicateExists = true;

        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create a new car.");
        }

        try {
            repository.getByIdentifications(car.getIdentifications());
            repository.getByPlate(car.getIdentifications());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (!trueCityIndexPlate(car.getRegistrationPlate())) {
            throw new IncorrectPlateRegistrationException(car.getRegistrationPlate(), car.getRegistrationPlate().substring(0, 1));
        }
        if (!trueNumberPlate(car.getRegistrationPlate()))
            throw new IncorrectPlateRegistrationException(car.getRegistrationPlate());

        if(!check(car.getRegistrationPlate())){
            throw new IncorrectPlateRegistrationException(car.getRegistrationPlate(), car.getRegistrationPlate().substring(6));
        }



        if (duplicateExists) {
            throw new DuplicateEntityException("Car", "identification number", car.getIdentifications());
        }

        repository.create(car);
    }

    public void update(Car car, User user) {
        if (!user.isEmployee()) {
            throw new UnauthorizedOperationException("Only employee can update shipment.");
        }

        repository.update(car);
    }

    @Override
    public void delete(int id, User user) {
        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee  can delete a car.");
        }
        Car car = new Car();
        try {
            car = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Car", "id", id);
        }
        repository.delete(id);
    }


    public boolean trueCityIndexPlate(String plate) {
        boolean flag = false;
        String cityIndex = plate;
        if(plate.length()==7){
           cityIndex = plate.substring(0, 1);
        }
        else {
            cityIndex = plate.substring(0, 2);
        }

        List<City> cityList = cityRepository.getAllCityIndex();

        for (City city : cityList) {
            if (cityIndex.equals(city.getCityIndex())) {
                flag = true;
                break;
            }

        }
        return flag;
    }

    public boolean trueNumberPlate(String plate) {
        boolean flag = true;
        String cityIndex = plate;
        if(plate.length()==7){
            cityIndex = plate.substring(1, 5);
        }
        else {
            cityIndex = plate.substring(2, 6);
        }
        try {
            int number = Integer.parseInt(cityIndex.trim());
        } catch (NumberFormatException nFE) {
            flag = false;
        }
        return flag;
    }

    boolean check(String plate) {

        String cityIndex = plate;
        if(plate.length()==7){
            cityIndex = plate.substring(5);
        }
        else {
            cityIndex = plate.substring(6);
        }

        int len = cityIndex.length();
        for (int i = 0; i < len; i++) {

            if ((!Character.isLetter(cityIndex.charAt(i)))) {
                return false;
            }
        }
        return true;
    }
}
