package com.java.smart_garage.exceptions;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String type, int id) {
        this(type, "id", String.valueOf(id));
    }

    public EntityNotFoundException(String type, String attribute, String value) {
        super(String.format("%s with %s %s not found.", type, attribute, value));
    }

    public EntityNotFoundException(String type, String attribute) {
        super(String.format("%s with %s not found.", type, attribute));
    }

    public EntityNotFoundException(String type, String attribute, int value) {
        super(String.format("%s with %s %s not found.", type, attribute, value));
    }

}
