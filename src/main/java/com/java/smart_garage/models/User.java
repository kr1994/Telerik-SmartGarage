package com.java.smart_garage.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;


    @ManyToOne
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    public User() {
    }


    public User(int userId,
                String username,
                String password,
                UserType userType) {

        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }



    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setUserType(UserType userType) {
        this.userType = userType;
    }


    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public UserType getUserType() {
        return userType;
    }

    @JsonIgnore
    public boolean isEmployee() {
        return getUserType().getType().equals("Employee");
    }
    @JsonIgnore
    public boolean isUser(String userName) {
        return getUsername().equals(userName);
    }
    @JsonIgnore
    public boolean isUserId(int id) {
        return getUserId()==id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        User user = (User) o;
        return this.getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

}


