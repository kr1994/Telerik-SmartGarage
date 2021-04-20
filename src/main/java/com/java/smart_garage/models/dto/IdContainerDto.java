package com.java.smart_garage.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class IdContainerDto {

    @NotNull
    @Positive(message = "User Id must be positive.")
    private int userId;

    @NotNull
    @Positive(message = "Personal Info Id must be positive.")
    private int personalInfoId;

    @NotNull
    @Positive(message = "User type Id must be positive.")
    private int userTypeId;

    @NotNull
    @Positive(message = "Credential Id must be positive.")
    private int credentialId;

    public IdContainerDto() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPersonalInfoId() {
        return personalInfoId;
    }

    public void setPersonalInfoId(int personalInfoId) {
        this.personalInfoId = personalInfoId;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public int getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(int credentialId) {
        this.credentialId = credentialId;
    }
}
