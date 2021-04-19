package com.java.smart_garage.models.dto;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public class CurrencyParamDto {

    private Optional<String> currency;

    public CurrencyParamDto() {
    }

    public Optional<String> getCurrency() {
        return currency;
    }

    public void setCurrency(Optional<String> currency) {
        this.currency = currency;
    }
}
