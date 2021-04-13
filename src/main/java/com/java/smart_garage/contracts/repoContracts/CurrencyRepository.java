package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.Currency;

import java.util.List;

public interface CurrencyRepository {
    Currency getCurrencyByName(String name);
}
