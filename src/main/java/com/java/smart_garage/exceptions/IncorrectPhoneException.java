package com.java.smart_garage.exceptions;

public class IncorrectPhoneException extends RuntimeException{
    public IncorrectPhoneException(String plate) {
        super(String.format("Phone %s  is not correct.", plate));
    }
}
