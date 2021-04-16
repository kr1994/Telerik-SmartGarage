package com.java.smart_garage.models.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RegisterDto {

    @NotEmpty
    @Size(min = 2, max = 20)
    private String username;

    @NotEmpty
    @Size(min = 8, max = 8)
    private String password;



}
