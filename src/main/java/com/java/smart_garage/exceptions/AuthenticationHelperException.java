package com.java.smart_garage.exceptions;

public class AuthenticationHelperException extends RuntimeException{

    public AuthenticationHelperException() {
    }

    public AuthenticationHelperException(String message) {
        super(message);
    }
}
