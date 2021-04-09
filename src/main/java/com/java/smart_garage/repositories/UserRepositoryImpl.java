package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.AutomobileRepository;
import com.java.smart_garage.contracts.repoContracts.UserRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<PersonalInfo> query = criteriaBuilder.createQuery(PersonalInfo.class);
            Predicate predicate = getPredicate(criteriaBuilder, query, firstName, lastName, email, phoneNumber);
            return session.createQuery(query.where(predicate)).getResultList();

            //return getPersonalInfoList(firstName, lastName, email, phoneNumber, session);
        }
    }

    private Predicate getCarPredicate(CriteriaBuilder criteriaBuilder,
                                      CriteriaQuery<PersonalInfo> query,
                                      AutomobileRepository automobileRepository,
                                      Optional<String> modelAutomobile) {

        try (Session session = sessionFactory.openSession()) {
            Query<PersonalInfo> queryForName = session.createQuery("from Model m join Automobile a join User u " +
                    "join PersonalInfo p where a.model.modelName = :modelAutomobile", PersonalInfo.class);
        }
        Root<PersonalInfo> root = query.from(PersonalInfo.class);
        List<Predicate> list = new ArrayList<>();

        if (modelAutomobile.isPresent()) {
            /* List<Automobile> automobiles = automobileRepository.getByOwner(firstName);

               Automobile automobile = automobiles
                    .stream()
                    .filter(a -> a.getModel().equals(modelAutomobile))
                    .collect(Collectors.toList()).get(0);



                Predicate automobilePredicate = criteriaBuilder.equal(root.get("firstName"), firstName);
                list.add(automobilePredicate);
              */
                //throw new EntityNotFoundException("Model ", "not found!");


        }
        return criteriaBuilder.and(list.toArray(Predicate[]::new));
    }

    private Predicate getPredicate(CriteriaBuilder criteriaBuilder,
                                   CriteriaQuery<PersonalInfo> query,
                                   Optional<String> firstName,
                                   Optional<String> lastName,
                                   Optional<String> email,
                                   Optional<String> phoneNumber) {

        Root<PersonalInfo> root = query.from(PersonalInfo.class);
        List<Predicate> list = new ArrayList<>();

        if (firstName.isPresent() && !firstName.get().isEmpty()) {
            Predicate firstNamePredicate = criteriaBuilder.like(root.get("firstName"), "%" + firstName.get() + "%");
            list.add(firstNamePredicate);
        }

        if (lastName.isPresent() && !lastName.get().isEmpty()) {
            Predicate lastNamePredicate = criteriaBuilder.like(root.get("lastName"), "%" + lastName.get() + "%");
            list.add(lastNamePredicate);
        }

        if (email.isPresent()) {
            Predicate emailPredicate = criteriaBuilder.equal(root.get("email"), email.get());
            list.add(emailPredicate);
        }

        if (phoneNumber.isPresent()) {
            Predicate phoneNumberPredicate = criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber.get());
            list.add(phoneNumberPredicate);
        }

        return criteriaBuilder.and(list.toArray(Predicate[]::new));

    }

    private List<PersonalInfo> getPersonalInfoList(Optional<String> firstName, Optional<String> lastName, Optional<String> email, Optional<String> phoneNumber, Session session) {
        int countParameters = 0;
        String queryStr = "from PersonalInfo p ";

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


        //return query.stream().filter(u -> !(u.isEmployee())).collect(Collectors.toList());
        return query.list();
    }
}