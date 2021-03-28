package com.java.smart_garage.repositories;

import com.java.smart_garage.models.CubicCapacity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import java.util.List;

public class CubicCapacityRepositoryImpl {

    private final SessionFactory sessionFactory;

    public CubicCapacityRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected List<CubicCapacity> getAll() {
        String queryString = String.format("from %s ", CubicCapacity.class);

        try (Session session = sessionFactory.openSession()) {
            Query<CubicCapacity> query = session.createQuery(
                    queryString, CubicCapacity.class);

            return query.list();
        }
    }

    public CubicCapacity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            CubicCapacity cc = session.get(CubicCapacity.class, id);
            if (cc == null) {
                create(cc);
            }
            return cc;
        }
    }


    public void create(CubicCapacity entity) {
        try (Session session = sessionFactory.openSession()) {
            session.save(entity);
        }
    }
}
