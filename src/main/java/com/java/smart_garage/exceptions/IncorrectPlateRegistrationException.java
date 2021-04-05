package com.java.smart_garage.exceptions;

public class IncorrectPlateRegistrationException extends RuntimeException{


    public IncorrectPlateRegistrationException(String plate) {
        super(String.format("Plate %s  is not correct.", plate));
    }



}
