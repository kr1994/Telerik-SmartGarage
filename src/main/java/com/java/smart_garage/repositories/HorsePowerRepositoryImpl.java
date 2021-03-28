package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.HorsePowerRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.HorsePower;
import com.java.smart_garage.models.Manufacturer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HorsePowerRepositoryImpl implements HorsePowerRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public HorsePowerRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<HorsePower> getAll() {
        // String queryString = String.format("from %s order by %s", HorsePower.class,"powerId");

        try (Session session = sessionFactory.openSession()) {
            Query<HorsePower> query = session.createQuery(
                    "from HorsePower order by powerId", HorsePower.class);

            return query.list();
        }
    }

    @Override
    public HorsePower getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            HorsePower horsePower = session.get(HorsePower.class, id);
            if (horsePower == null) {
                throw new EntityNotFoundException("Category", "id", id);
            }
            return horsePower;
        }
    }

    @Override
    public HorsePower create(HorsePower entity) {
        try (Session session = sessionFactory.openSession()) {
            session.save(entity);
        }
        return entity;
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(Manufacturer.class, id));
            session.getTransaction().commit();
        }
    }


}

