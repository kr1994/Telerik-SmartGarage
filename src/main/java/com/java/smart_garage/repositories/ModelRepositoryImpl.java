package com.java.smart_garage.repositories;


import com.java.smart_garage.contracts.repoContracts.ModelRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.Model;
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
    public List<Model> getAllModels() {
        try (Session session = sessionFactory.openSession()) {
            Query<Model> query = session.createQuery("from Model order by modelId",
                    Model.class);
            return query.list();
        }
    }


    @Override
    public Model getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Model model = session.get(Model.class, id);
            if (model == null) {
                throw new EntityNotFoundException("Model", "id", id);
            }

            return model;
        }
    }

    @Override
    public Model getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Model> query = session.createQuery("from Model where modelName like concat('%', :name, '%')",
                    Model.class);
            query.setParameter("name", name);
            List<Model> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Model", "name", name);
            }
            return result.get(0);
        }

    }


    @Override
    public Model create(Model model) {
        try (Session session = sessionFactory.openSession()) {
            session.save(model);
        }

        return model;
    }


    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(Model.class, id));
            session.getTransaction().commit();
        }
    }

}
