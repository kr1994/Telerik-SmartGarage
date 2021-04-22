package com.java.smart_garage.repositories;


import com.java.smart_garage.contracts.repoContracts.ModelRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.ModelCar;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModelRepositoryImpl implements ModelRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public ModelRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<ModelCar> getAllModels() {
        try (Session session = sessionFactory.openSession()) {
            Query<ModelCar> query = session.createQuery("from ModelCar order by manufacturer.manufacturerName ",
                    ModelCar.class);
            return query.list();
        }
    }


    @Override
    public ModelCar getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            ModelCar modelCar = session.get(ModelCar.class, id);
            if (modelCar == null) {
                throw new EntityNotFoundException("Model", "id", id);
            }

            return modelCar;
        }
    }

    @Override
    public ModelCar getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<ModelCar> query = session.createQuery("from ModelCar where model like concat('%', :name, '%')",
                    ModelCar.class);
            query.setParameter("name", name);
            List<ModelCar> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Model", "name", name);
            }
            return result.get(0);
        }

    }


    @Override
    public ModelCar create(ModelCar modelCar) {
        try (Session session = sessionFactory.openSession()) {
            session.save(modelCar);
        }

        return modelCar;
    }


    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(ModelCar.class, id));
            session.getTransaction().commit();
        }
    }

}
