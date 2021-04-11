package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.AutomobileRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AutomobileRepositoryImpl implements AutomobileRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public AutomobileRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Automobile> getAllCars() {
        try (Session session = sessionFactory.openSession()) {
            Query<Automobile> query = session.createQuery("from Automobile",
                    Automobile.class);
            return query.list();
        }
    }


    @Override
    public Automobile getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Automobile automobile = session.get(Automobile.class, id);
            if (automobile == null) {
                throw new EntityNotFoundException("Car", "id", id);
            }
            return automobile;
        }
    }

    @Override
    public List<Automobile> customerId(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<Automobile> query = session.createQuery("from Automobile where user.id = :id",
                    Automobile.class);
            query.setParameter("id", id);
            List<Automobile> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Owner", "with", id);
            }
            return result;
        }
    }

    @Override
    public Automobile getByIdentifications(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Automobile> query = session.createQuery("from Automobile where identifications like :name",
                    Automobile.class);
            query.setParameter("name", name);
            List<Automobile> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Identification", "value", name);
            }
            return result.get(0);
        }
    }

    @Override
    public Automobile getByPlate(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Automobile> query = session.createQuery("from Automobile where registrationPlate like :name",
                    Automobile.class);
            query.setParameter("name", name);
            List<Automobile> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Plate", "value", name);
            }
            return result.get(0);
        }
    }

    @Override
    public Automobile create(Automobile automobile) {
        try (Session session = sessionFactory.openSession()) {
            session.save(automobile);
        }

        return automobile;
    }

    @Override
    public Automobile update(Automobile automobile) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(automobile);
            session.getTransaction().commit();
        }

        return automobile;
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(Automobile.class, id));
            session.getTransaction().commit();
        }
    }


}
