package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.PersonalInfoRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.PersonalInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonalInfoRepositoryImpl implements PersonalInfoRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonalInfoRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<PersonalInfo> getAllPersonalInformations() {
        // String queryString = String.format("from %s order by %s", PersonalInfo.class,"personalId");

        try (Session session = sessionFactory.openSession()) {
            Query<PersonalInfo> query = session.createQuery(
                    "from PersonalInfo order by personalId", PersonalInfo.class);

            return query.list();
        }
    }

    @Override
    public PersonalInfo getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            PersonalInfo service = session.get(PersonalInfo.class, id);
            if (service == null) {
                throw new EntityNotFoundException("Category", "id", id);
            }
            return service;
        }
    }

    @Override
    public PersonalInfo getByFirstName(String firstName) {
        try (Session session = sessionFactory.openSession()){
            Query<PersonalInfo> query = session.createQuery("from PersonalInfo where firstName like concat('%', :name, '%')",
                    PersonalInfo.class);
            query.setParameter("name", firstName);
            List<PersonalInfo> result = query.list();

            if (result.size() == 0) {
                throw new EntityNotFoundException("Personal Information", "name", firstName);
            }
            return result.get(0);
        }
    }

    @Override
    public PersonalInfo getByLastName(String lastName) {
        try (Session session = sessionFactory.openSession()){
            Query<PersonalInfo> query = session.createQuery("from PersonalInfo where lastName like concat('%', :name, '%')",
                    PersonalInfo.class);
            query.setParameter("name", lastName);
            List<PersonalInfo> result = query.list();

            if (result.size() == 0) {
                throw new EntityNotFoundException("Personal Information", "name", lastName);
            }
            return result.get(0);
        }
    }

    @Override
    public PersonalInfo getByEmail(String email) {
        try (Session session = sessionFactory.openSession()){
            Query<PersonalInfo> query = session.createQuery("from PersonalInfo where email like concat('%', :name, '%')",
                    PersonalInfo.class);
            query.setParameter("name", email);
            List<PersonalInfo> result = query.list();

            if (result.size() == 0) {
                throw new EntityNotFoundException("Personal Information", "name", email);
            }
            return result.get(0);
        }
    }

    @Override
    public PersonalInfo create(PersonalInfo entity) {
        try (Session session = sessionFactory.openSession()) {
            session.save(entity);
        }
        return entity;
    }

    @Override
    public PersonalInfo update(PersonalInfo entity) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw new RuntimeException(e.toString());
        }

        return entity;
    }

    @Override
    public void delete(int id){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(session.get(PersonalInfo.class,id));
            session.getTransaction().commit();
        }
    }

}

