package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.WorkServiceRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.CarService;
import com.java.smart_garage.models.WorkService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class WorkServiceRepositoryImpl implements WorkServiceRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public WorkServiceRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<WorkService> getAllWorkServices() {
        // String queryString = String.format("from %s order by %s", WorkService.class,"serviceId");

        try (Session session = sessionFactory.openSession()) {
            Query<WorkService> query = session.createQuery(
                    "from WorkService order by workServiceId", WorkService.class);

            return query.list();
        }
    }

    @Override
    public WorkService getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<WorkService> query = session.createQuery("from WorkService where workServiceName like concat('%', :name, '%')",
                    WorkService.class);
            query.setParameter("name", name);
            List<WorkService> result = query.list();

            if (result.size() == 0) {
                throw new EntityNotFoundException("Work Service", "name", name);
            }
            return result.get(0);
        }
    }

    @Override
    public WorkService getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            WorkService service = session.get(WorkService.class, id);
            if (service == null) {
                throw new EntityNotFoundException("Work Service", "id", id);
            }
            return service;
        }
    }

    @Override
    public WorkService create(WorkService entity) {
        try (Session session = sessionFactory.openSession()) {
            session.save(entity);
        }
        return entity;
    }

    @Override
    public WorkService update(WorkService entity) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw new RuntimeException(e.toString());
        }

        return entity;
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(WorkService.class, id));
            session.getTransaction().commit();
        }
    }

    @Override
    public List<WorkService> filterWorkServicesByNameAndPrice(Optional<String> name,
                                                              Optional<Double> price) {

        try (Session session = sessionFactory.openSession()) {

            int countParameters = 0;
            String queryStr = "from WorkService ws ";

            if (name.isPresent()) {
                queryStr += "where workServiceName = :name ";
                countParameters++;
            }

            if (price.isPresent()) {
                if (countParameters > 0) {
                    queryStr += "and workServicePrice = :price "; // = or < ????
                } else {
                    queryStr += "where workServicePrice = :price ";  // = or < ????
                }
                countParameters++;
            }

            Query<WorkService> query = session.createQuery(queryStr, WorkService.class);

            name.ifPresent(s -> query.setParameter("name", s));

            price.ifPresent(aDouble -> query.setParameter("price", aDouble));

            return query.getResultList();
        }
    }
}
