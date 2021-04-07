package com.java.smart_garage.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static com.java.smart_garage.models.ModelsConstants.ModelsConstants.EMPLOYEE;

@Entity
@Table(name = "credentials")
public class Credential {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "credential_id")
    private int credentialId;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;


    public Credential() {
    }


    public Credential(int credentialId,
                      String username,
                      String password,
                      UserType userType) {

        this.credentialId = credentialId;
        this.username = username;
        this.password = password;
        }


    public void setCredentialId(int userId) {
        this.credentialId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }




    public int getCredentialId() {
        return credentialId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }




    @JsonIgnore
    public boolean isUser(String userName) {
        return getUsername().equals(userName);
    }
    @JsonIgnore
    public boolean isUserId(int id) {
        return getCredentialId()==id;
    }


    /*
    public void filterCustomersByFirstName() {
        CriteriaQuery<Customer> criteriaQuery =
                criteriaBuilder.createQuery(Customer.class);
        Root<Customer> root = criteriaQuery.from(Customer.class);
        CriteriaBuilder.In<String> inClause = criteriaBuilder.in(root.get("firstName"));
        for (String title : titles) {
            inClause.value(title);
        }
        criteriaQuery.select(root).where(inClause);

        Subquery<User> subquery = criteriaQuery.subquery(User.class);
        Root<User> user = subquery.from(User.class);
        subquery.select(user)
                .distinct(true)
                .where(criteriaBuilder.like(user.get("name"), "%" + searchKey + "%"));

        criteriaQuery.select(customer)
                .where(criteriaBuilder.in(customer.get("firstName")).value(subquery));
    }

    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Credential credential = (Credential) o;
        return this.getUsername().equals(credential.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

}


