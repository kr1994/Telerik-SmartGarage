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
            Query<Car> query = session.createQuery("from Car order by carId",
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
    public Car create(Car car) {
        try (Session session = sessionFactory.openSession()) {
            session.save(car);
        }

        return car;
    }

    @Override
    public Car update(Car car, Model model,
                      RegistrationPlate registrationPlate,
                      Identification identification,
                      Year year,
                      Colour colour,
                      Engine engine) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            carUpdate(car,session,model,registrationPlate,identification,year,colour,engine);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw new RuntimeException(e.toString());
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

    private void carUpdate (Car car, Session session, Model model, RegistrationPlate registrationPlate, Identification identification,
                            Year year, Colour colour, Engine engine)
    {
        car.setModel(model);
        car.setRegistrationPlate(registrationPlate);
        car.setIdentifications(identification);
        car.setYear(year);
        car.setColour(colour);
        car.setEngine(engine);
        session.update(model);
        session.update(registrationPlate);
        session.update(identification);
        session.update(year);
        session.update(colour);
        session.update(engine);
        session.update(car);
    }

}
