package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.RegistrationPlateRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.Manufacturer;
import com.java.smart_garage.models.RegistrationPlate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RegistrationPlateRepositoryImpl implements RegistrationPlateRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public RegistrationPlateRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<RegistrationPlate> getAll() {
        // String queryString = String.format("from %s order by %s", RegistrationPlate.class,"plateId");

        try (Session session = sessionFactory.openSession()) {
            Query<RegistrationPlate> query = session.createQuery(
                    "from RegistrationPlate order by plateId", RegistrationPlate.class);

            return query.list();
        }
    }

    @Override
    public RegistrationPlate getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            RegistrationPlate registrationPlate = session.get(RegistrationPlate.class, id);
            if (registrationPlate == null) {
                throw new EntityNotFoundException("Category", "id", id);
            }
            return registrationPlate;
        }
    }

    @Override
    public RegistrationPlate getByName(String name) {
        try (Session session = sessionFactory.openSession()){
            Query<RegistrationPlate> query = session.createQuery("from RegistrationPlate where plateNumber like concat('%', :name, '%')",
                    RegistrationPlate.class);
            query.setParameter("name", name);
            List<RegistrationPlate> result = query.list();

            if (result.size() == 0) {
                throw new EntityNotFoundException("Registration Plate", "name", name);
            }
            return result.get(0);
        }
    }

    @Override
    public RegistrationPlate create(RegistrationPlate entity) {
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


