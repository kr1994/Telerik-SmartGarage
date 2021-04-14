package com.java.smart_garage.exceptions;

public class NoConnectionWithTheUrlException extends RuntimeException {

    public NoConnectionWithTheUrlException(String url) {
        super(String.format("No connection with %s.", url));
    }
}
