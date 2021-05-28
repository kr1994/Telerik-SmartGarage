package com.java.smart_garage.models.dto;

import javax.validation.constraints.NotNull;
import java.sql.Date;

public class FilterDateCurrencyDto {

    private Date startingDate;


    private Date endingDate;

    private String currency;
}
