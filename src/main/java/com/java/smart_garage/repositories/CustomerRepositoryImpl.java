package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.CustomerRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public CustomerRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Customer> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Customer> query = session.createQuery("from Customer order by customerId",
                    Customer.class);
            return query.list();
        }
    }

    @Override
    public Customer getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Customer customer = session.get(Customer.class, id);
            if (customer == null) {
                throw new EntityNotFoundException("Customer", "id", id);
            }
            return customer;
        }
    }

    @Override
    public Customer create(Customer customer) {
        try (Session session = sessionFactory.openSession()) {
            session.save(customer);
        }

        return customer;
    }

    @Override
    public Customer update(Customer customer,
                           User user,
                           int phoneNumber) {

        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            customerUpdate(customer, session, user, phoneNumber);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw new RuntimeException(e.toString());
        }

        return customer;
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(Customer.class, id));
            session.getTransaction().commit();
        }
    }

    private void customerUpdate(Customer customer,
                                Session session,
                                User user,
                                int phoneNumber) {

        customer.setUser(user);
        customer.setPhoneNumber(phoneNumber);
        session.update(user);
        session.update(phoneNumber);
        session.update(customer);
    }
}