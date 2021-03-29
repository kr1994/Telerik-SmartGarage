package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.CubicCapacityRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.CubicCapacity;
import com.java.smart_garage.models.Manufacturer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CubicCapacityRepositoryImpl implements CubicCapacityRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public CubicCapacityRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<CubicCapacity> getAllCubicCapacities() {
       // String queryString = String.format("from %s order by %s", CubicCapacity.class,"cubicCapacityId");

        try (Session session = sessionFactory.openSession()) {
            Query<CubicCapacity> query = session.createQuery(
                    "from CubicCapacity order by cubicCapacityId", CubicCapacity.class);

            return query.list();
        }
    }

    @Override
    public CubicCapacity getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            CubicCapacity cc = session.get(CubicCapacity.class, id);
            if (cc == null) {
                throw new EntityNotFoundException("Category", "id", id);
            }
            return cc;
        }
    }

    @Override
    public CubicCapacity create(CubicCapacity entity) {
        try (Session session = sessionFactory.openSession()) {
            session.save(entity);
        }
        return entity;
    }

    @Override
    public void delete(int id){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(session.get(Manufacturer.class,id));
            session.getTransaction().commit();
        }
    }


}
