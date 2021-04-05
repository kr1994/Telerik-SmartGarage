package com.java.smart_garage.contracts.serviceContracts;

public interface PlateValidationService {
    boolean trueCityIndexPlate(String plate);

    boolean trueNumberPlate(String plate);

    boolean check(String plate);
}
