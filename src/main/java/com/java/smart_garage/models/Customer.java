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
    @Column(name = "user_id")
    private User user;

    @Column(name = "phone_number")
    private int phoneNumber;

    public Customer() {
    }

    public Customer(int customerId, User user, int phoneNumber) {
        this.customerId = customerId;
        this.user = user;
        this.phoneNumber = phoneNumber;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public User getUser() {
        return user;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

}
