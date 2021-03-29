package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.Year;

import java.util.List;

public interface YearRepository {
    List<Year> getAllYears();

    Year getById(int id);

    Year getByYear(int year);

    Year create(Year year);

    void delete(int id);
}
