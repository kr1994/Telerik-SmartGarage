package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.User;
import com.java.smart_garage.models.Year;

import java.util.List;

public interface YearService {
    List<Year> getAllYears();

    Year getById(int id);

    Year getExactYear(int year);

    void create(Year year, User user);

    void delete(int id, User user);
}
