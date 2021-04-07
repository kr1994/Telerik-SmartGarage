package com.java.smart_garage.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import static com.java.smart_garage.models.ModelsConstants.ModelsConstants.EMPLOYEE;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @OneToOne
    @JoinColumn(name = "credential_id")
    private Credential credential;

    @OneToOne
    @JoinColumn(name = "personal_info_id")
    private PersonalInfo personalInfo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "type_id")
    private UserType userType;

    public User() {
    }

    public User(int userId, Credential credential, PersonalInfo personalInfo, UserType userType) {
        this.userId = userId;
        this.credential = credential;
        this.personalInfo = personalInfo;
        this.userType = userType;
    }

    public void setUserId(int customerId) {
        this.userId = customerId;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }


    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public Credential getCredential() {
        return credential;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public UserType getUserType() {
        return userType;
    }

    @JsonIgnore
    public boolean isEmployee() {
        return getUserType().getType().equals(EMPLOYEE);
    }
}
