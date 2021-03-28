package com.java.smart_garage.exceptions;

public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(String type, String attribute, String value) {
        super(String.format("%s with %s %s already exists.", type, attribute, value));
    }

    public DuplicateEntityException(String type, String userName, String email, String valueUn, String valueEmail) {
        super(String.format("%s with %s/%s %s/%s already exists.", type, userName, email, valueUn, valueEmail));
    }

    public DuplicateEntityException(String type, String attribute, int value) {
        super(String.format("%s with %s %s already exists.", type, attribute, value));
    }
}
