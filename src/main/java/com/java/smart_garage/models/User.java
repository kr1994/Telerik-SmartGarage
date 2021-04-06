package com.java.smart_garage.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.criterion.CriteriaQuery;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;
import java.util.Objects;

import static com.java.smart_garage.models.ModelsConstants.ModelsConstants.EMPLOYEE;

@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    @OneToMany(mappedBy = "")
    private List<Customer> allCustomers;

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
        return getUserType().getType().equals(EMPLOYEE);
    }
    @JsonIgnore
    public boolean isUser(String userName) {
        return getUsername().equals(userName);
    }
    @JsonIgnore
    public boolean isUserId(int id) {
        return getUserId()==id;
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
        User user = (User) o;
        return this.getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

}


