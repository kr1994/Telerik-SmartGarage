package com.java.smart_garage.models.dto;

import javax.validation.constraints.NotNull;

public class UserTypeDto {

    @NotNull
    private String type;

    public UserTypeDto() {
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
