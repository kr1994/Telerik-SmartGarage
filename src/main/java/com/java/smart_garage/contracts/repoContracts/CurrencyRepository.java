package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.Currency;

import java.util.List;

public interface CurrencyRepository {

    List<Currency> getAllCurrencies();
    Currency getCurrencyByName(String name);
}
