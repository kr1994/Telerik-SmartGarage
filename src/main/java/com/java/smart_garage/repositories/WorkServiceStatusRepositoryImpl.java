package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.WorkServiceStatusRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;

import com.java.smart_garage.models.WorkServiceStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkServiceStatusRepositoryImpl implements WorkServiceStatusRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public WorkServiceStatusRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<WorkServiceStatus> getAllStatuses(){
        try(Session session =  sessionFactory.openSession()) {
            Query<WorkServiceStatus> query = session.createQuery(
                    "from WorkServiceStatus ", WorkServiceStatus.class);

            return query.list();
        }
    }
    @Override
    public WorkServiceStatus getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            WorkServiceStatus status = session.get(WorkServiceStatus.class, id);
            if (status == null) {
                throw new EntityNotFoundException("Status", "id", id);
            }

            return status;
        }
    }

}
