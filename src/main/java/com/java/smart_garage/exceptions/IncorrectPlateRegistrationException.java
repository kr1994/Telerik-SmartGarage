package com.java.smart_garage.exceptions;

public class IncorrectPlateRegistrationException extends RuntimeException{


    public IncorrectPlateRegistrationException(String plate, String attribute) {
        super(String.format("Plate %s with %s  index is not correct.", plate, attribute));
    }
    public IncorrectPlateRegistrationException( String plate) {
        super(String.format("Plate %s is incorrect number input.", plate));
    }


}
