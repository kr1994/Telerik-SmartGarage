package com.java.smart_garage.services;

import com.java.smart_garage.contracts.repoContracts.CityRepository;
import com.java.smart_garage.contracts.serviceContracts.PlateValidationService;
import com.java.smart_garage.models.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlateValidationServiceImpl implements PlateValidationService {

    public static final int PLATE_WITH_SINGLE_CHARACTER_INDEX = 7;
    private final CityRepository cityRepository;

    @Autowired
    public PlateValidationServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public boolean trueCityIndexPlate(String plate) {
        boolean flag = false;
        String cityIndex = plate;
        if(plate.length()==PLATE_WITH_SINGLE_CHARACTER_INDEX){
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

    @Override
    public boolean trueNumberPlate(String plate) {
        boolean flag = true;
        String cityIndex = plate;
        if(plate.length()==PLATE_WITH_SINGLE_CHARACTER_INDEX){
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

    @Override
    public boolean check(String plate) {

        String cityIndex = plate;
        if(plate.length()==PLATE_WITH_SINGLE_CHARACTER_INDEX){
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
            if(cityIndex.length()>2){
                return false;
            }
        }
        return true;
    }
}
