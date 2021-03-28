package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.UserTypeRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.UserType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserTypeRepositoryImpl implements UserTypeRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserTypeRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<UserType> getAllUserTypes() {
        try (Session session = sessionFactory.openSession()) {
            Query<UserType> query = session.createQuery("from UserType order by typeId",
                    UserType.class);
            return query.list();
        }
    }


    @Override
    public UserType getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            UserType type = session.get(UserType.class, id);
            if (type == null) {
                throw new EntityNotFoundException("UserType", "id", id);
            }

            return type;
        }
    }

    @Override
    public UserType getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<UserType> query = session.createQuery("from UserType where type like concat('%', :name, '%')",
                    UserType.class);
            query.setParameter("name", name);
            List<UserType> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("UserType", "name", name);
            }
            return result.get(0);
        }

    }

    @Override
    public UserType create(UserType type) {
        try (Session session = sessionFactory.openSession()) {
            session.save(type);
        }

        return type;
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(UserType.class, id));
            session.getTransaction().commit();
        }
    }
}
