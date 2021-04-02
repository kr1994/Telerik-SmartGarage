package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.CarRepository;
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
public class CarRepositoryImpl implements CarRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public CarRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Car> getAllCars() {
        try (Session session = sessionFactory.openSession()) {
            Query<Car> query = session.createQuery("from Car",
                    Car.class);
            return query.list();
        }
    }

    @Override
    public Car getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Car car = session.get(Car.class, id);
            if (car == null) {
                throw new EntityNotFoundException("Car", "id", id);
            }
            return car;
        }
    }

    @Override
    public Car getByIdentifications(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Car> query = session.createQuery("from Car where identifications like :name",
                    Car.class);
            query.setParameter("name", name);
            List<Car> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Identification", "value", name);
            }
            return result.get(0);
        }
    }

    @Override
    public Car create(Car car) {
        try (Session session = sessionFactory.openSession()) {
            session.save(car);
        }

        return car;
    }

    @Override
    public Car update(Car car) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(car);
            session.getTransaction().commit();
        }

        return car;
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(Car.class, id));
            session.getTransaction().commit();
        }
    }


}
