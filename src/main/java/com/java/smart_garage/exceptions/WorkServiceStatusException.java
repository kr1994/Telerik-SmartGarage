package com.java.smart_garage.exceptions;

public class WorkServiceStatusException extends RuntimeException{
    public WorkServiceStatusException(String workService) {
        super(String.format("%s is not active.", workService));
    }

}
