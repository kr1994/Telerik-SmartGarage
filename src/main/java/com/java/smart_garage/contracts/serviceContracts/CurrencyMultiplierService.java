package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Currency;

import java.util.List;

public interface CurrencyMultiplierService {

    List<Currency> getAllCurrency();

    double getCurrency(String value);
}
