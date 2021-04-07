package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.UserRepository;
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
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User order by userId",
                    User.class);
            return query.list();
        }
    }

    @Override
    public User getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User", "id", id);
            }
            return user;
        }
    }

    @Override
    public User create(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.save(user);
        }

        return user;
    }

    @Override
    public User update(User user,
                       Credential credential,
                       PersonalInfo personalInfo) {

        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            userUpdate(user, session, credential, personalInfo);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw new RuntimeException(e.toString());
        }

        return user;
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        }
    }

    private void userUpdate(User user,
                                Session session,
                                Credential credential,
                                PersonalInfo personalInfo) {

        user.setCredential(credential);
        user.setPersonalInfo(personalInfo);
        session.update(credential);
        session.update(personalInfo);
        session.update(user);
    }

    @Override
    public void filterUsersByFirstName(String searchKey) {

        /*
        try (Session session = sessionFactory.openSession()) {
            Query<Customer> query = session.createQuery("from User order by userId",
                    Customer.class);

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            query.

            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);

            //CriteriaBuilder.In<String> inClause = criteriaBuilder.in(root.get("firstName"));

            //criteriaQuery.select(root).where(inClause);
            //criteriaQuery.select(root).where(criteriaBuilder.gt(root.get("itemPrice"), 1000));
            criteriaQuery.select(root).where(criteriaBuilder.like(root.get("firstName"), searchKey));



            Subquery<Customer> subquery = criteriaQuery.subquery(Customer.class);
            Root<Customer> customer = subquery.from(Customer.class);
            subquery.select(customer)
                    .distinct(true)
                    .where(criteriaBuilder.like(customer.get("firstName"), "%" + searchKey + "%"));

            criteriaQuery.select(customer)
                    .where(criteriaBuilder.in(customer.get("firstName")).value(subquery));
           }
          */
    }
}