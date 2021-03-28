package com.java.smart_garage.exceptions;

public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(String type, int id) {
        this(type, "id", String.valueOf(id));
    }

    public DuplicateEntityException(String entity, String attribute, String value) {

        super(String.format("%s with %s %s already exists", entity, attribute, value));
    }

    public DuplicateEntityException(String message) {
        super(message);
    }
}
