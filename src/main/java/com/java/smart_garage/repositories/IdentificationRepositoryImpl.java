package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.IdentificationRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.Identification;
import com.java.smart_garage.models.Manufacturer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IdentificationRepositoryImpl implements IdentificationRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public IdentificationRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Identification> getAll() {
        // String queryString = String.format("from %s order by %s", Identification.class,"identificationId");

        try (Session session = sessionFactory.openSession()) {
            Query<Identification> query = session.createQuery(
                    "from Identification order by identificationId", Identification.class);

            return query.list();
        }
    }

    @Override
    public Identification getByName(String name) {
        try (Session session = sessionFactory.openSession()){
            Query<Identification> query = session.createQuery("from Identification where identification like concat('%', :name, '%')",
                    Identification.class);
            query.setParameter("name", name);
            List<Identification> result = query.list();

            if (result.size() == 0) {
                throw new EntityNotFoundException("Identification", "name", name);
            }
            return result.get(0);
        }
    }

    @Override
    public Identification getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Identification identification = session.get(Identification.class, id);
            if (identification == null) {
                throw new EntityNotFoundException("Category", "id", id);
            }
            return identification;
        }
    }

    @Override
    public Identification create(Identification entity) {
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