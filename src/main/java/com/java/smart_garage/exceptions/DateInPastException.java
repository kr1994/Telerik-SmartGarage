package com.java.smart_garage.exceptions;

import java.util.Date;

public class DateInPastException extends RuntimeException{
    public DateInPastException(Date date) {
        super(String.format("%s is in the past", date));
    }
}
