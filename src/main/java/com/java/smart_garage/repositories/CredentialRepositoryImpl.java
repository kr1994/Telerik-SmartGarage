package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.CredentialRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CredentialRepositoryImpl implements CredentialRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public CredentialRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Credential> getAllCredentials() {
        try (Session session = sessionFactory.openSession()) {
            Query<Credential> query = session.createQuery("from Credential order by credentialId",
                    Credential.class);
            return query.list();
        }
    }

    @Override
    public Credential getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Credential credential = session.get(Credential.class, id);
            if (credential == null) {
                throw new EntityNotFoundException("Credential", "id", id);
            }
            return credential;
        }
    }

    @Override
    public Credential getByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<Credential> query = session.createQuery("from Credential where username like concat('%', :name, '%')",
                    Credential.class);
            query.setParameter("name", username);
            List<Credential> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Credential", "name", username);
            }
            return result.get(0);
        }
    }

    @Override
    public Credential create(Credential credential) {
        try (Session session = sessionFactory.openSession()) {
            session.save(credential);
        }

        return credential;
    }

    @Override
    public Credential update(Credential credential) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.update(credential);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw new RuntimeException(e.toString());
        }

        return credential;
    }

    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(Credential.class, id));
            session.getTransaction().commit();
        }
    }

}
