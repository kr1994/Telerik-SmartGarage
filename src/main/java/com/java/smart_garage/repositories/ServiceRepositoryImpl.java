package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.ServiceRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.Service;
import com.java.smart_garage.models.Manufacturer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ServiceRepositoryImpl implements ServiceRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ServiceRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Service> getAll() {
        // String queryString = String.format("from %s order by %s", Service.class,"serviceId");

        try (Session session = sessionFactory.openSession()) {
            Query<Service> query = session.createQuery(
                    "from Service order by serviceId", Service.class);

            return query.list();
        }
    }

    @Override
    public Service getByName(String name) {
        try (Session session = sessionFactory.openSession()){
            Query<Service> query = session.createQuery("from Service where serviceName like concat('%', :name, '%')",
                    Service.class);
            query.setParameter("serviceName", name);
            List<Service> result = query.list();

            if (result.size() == 0) {
                throw new EntityNotFoundException("Service", "name", name);
            }
            return result.get(0);
        }
    }

    @Override
    public Service getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Service service = session.get(Service.class, id);
            if (service == null) {
                throw new EntityNotFoundException("Category", "id", id);
            }
            return service;
        }
    }

    @Override
    public Service create(Service entity) {
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
