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

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                       PersonalInfo personalInfo,
                       UserType userType) {

        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            userUpdate(user, session, credential, personalInfo, userType);
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
                            PersonalInfo personalInfo,
                            UserType userType) {

        user.setCredential(credential);
        user.setPersonalInfo(personalInfo);
        user.setUserType(userType);
        session.update(credential);
        session.update(personalInfo);
        session.update(userType);
        session.update(user);
    }

    @Override
    public List<PersonalInfo> filterCustomers(Optional<String> firstName,
                                      Optional<String> lastName,
                                      Optional<String> email,
                                      Optional<String> phoneNumber) {

        try (Session session = sessionFactory.openSession()) {

            /*
            if (firstName.isPresent() && lastName.isPresent() && email.isPresent() && phoneNumber.isPresent()
                    && modelCar.isPresent() && visitsInRange.isPresent()) {
                query = session.createQuery("from User u join Automobile c where u.personalInfo.firstName = :firstName" +
                        " and u.personalInfo.lastName = :lastName and u.personalInfo.email = :email " +
                        " and u.personalInfo.phoneNumber = :phoneNumber and c.model = :modelCar", User.class);
                query.setParameter("firstName", firstName);
                query.setParameter("lastName", lastName);
                query.setParameter("email", email);
                query.setParameter("phoneNumber", phoneNumber);
                query.setParameter("modelCar", modelCar);
            } else {

             */
            int countParameters = 0;
            String queryStr = "from PersonalInfo p ";

            /*
            if (modelCar.isPresent()) {
                queryStr += "join Car c where c.model = :modelCar";
                countParameters++;
            }
            */
            if (firstName.isPresent()) {
                if (countParameters > 0) {
                    queryStr += " and p.firstName = :firstName";
                } else {
                    queryStr += "where p.firstName = :firstName";
                }
                countParameters++;
            }

            if (lastName.isPresent()) {
                if (countParameters > 0) {
                    queryStr += " and p.lastName = :lastName";
                } else {
                    queryStr += "where p.lastName = :lastName";
                }
                countParameters++;
            }

            if (email.isPresent()) {
                if (countParameters > 0) {
                    queryStr += " and p.email = :email";
                } else {
                    queryStr += "where p.email = :email";
                }
                countParameters++;
            }

            if (phoneNumber.isPresent()) {
                if (countParameters > 0) {
                    queryStr += " and p.phoneNumber = :phoneNumber";
                } else {
                    queryStr += "where p.phoneNumber = :phoneNumber";
                }
                countParameters++;
            }

            Query<PersonalInfo> query = session.createQuery(queryStr, PersonalInfo.class);

            if (firstName.isPresent()) {
                query.setParameter("firstName", firstName.get());
            }

            if (lastName.isPresent()) {
                query.setParameter("lastName", lastName.get());
            }

            if (email.isPresent()) {
                query.setParameter("email", email.get());
            }

            if (phoneNumber.isPresent()) {
                query.setParameter("phoneNumber", phoneNumber.get());
            }


            //query.setParameter("modelCar", modelCar.orElse(""));


            /*  } */

            //return query.stream().filter(u -> !(u.isEmployee())).collect(Collectors.toList());
            return query.list();
        }
    }
}