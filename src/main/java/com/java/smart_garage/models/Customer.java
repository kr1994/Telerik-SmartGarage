package com.java.smart_garage.models;


import javax.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "personal_info_id")
    private PersonalInfo personalInfo;

    public Customer() {
    }

    public Customer(int customerId, User user, PersonalInfo personalInfo) {
        this.customerId = customerId;
        this.user = user;
        this.personalInfo = personalInfo;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public int getCustomerId() {
        return customerId;
    }

    public User getUser() {
        return user;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }
}
