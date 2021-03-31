package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.EngineRepository;
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
public class EngineRepositoryImpl implements EngineRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public EngineRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Engine> getAllEngines() {
        try (Session session = sessionFactory.openSession()) {
            Query<Engine> query = session.createQuery("from Engine order by engineId",
                    Engine.class);
            return query.list();
        }
    }

    @Override
    public Engine getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Engine engine = session.get(Engine.class, id);
            if (engine == null) {
                throw new EntityNotFoundException("Engine", "id", id);
            }
            return engine;
        }
    }

    @Override
    public Engine create(Engine engine) {
        try (Session session = sessionFactory.openSession()) {
            session.save(engine);
        }

        return engine;
    }

    @Override
    public Engine update(Engine engine,
                         int horsePower,
                         Fuel fuel,
                         int cubicCapacity) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            engineUpdate(engine, session, horsePower, fuel, cubicCapacity);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw new RuntimeException(e.toString());
        }

        return engine;
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(Engine.class, id));
            session.getTransaction().commit();
        }
    }

    private void engineUpdate (Engine engine,
                               Session session,
                               int horsePower,
                               Fuel fuel,
                               int cubicCapacity)
    {
        engine.setHpw(horsePower);
        engine.setFuel(fuel);
        engine.setCubicCapacity(cubicCapacity);
        session.update(horsePower);
        session.update(fuel);
        session.update(cubicCapacity);
        session.update(engine);
    }

}
