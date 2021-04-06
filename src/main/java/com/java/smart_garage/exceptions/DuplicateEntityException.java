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
    public DuplicateEntityException(String type, String attribute1, int value1, String attribute2, String value2, String attribute3, int value3 ) {
        super(String.format("%s with %s - %s, %s-%s, %s-%s already exists.", type, attribute1, value1,attribute2,value2,attribute3,value3));
    }
}
