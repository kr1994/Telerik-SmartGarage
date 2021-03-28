package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.ColoursRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.Colour;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ColourRepositoryImpl implements ColoursRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ColourRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Colour> getAllColours() {
        try (Session session = sessionFactory.openSession()) {
            Query<Colour> query = session.createQuery("from Colour order by colourId", Colour.class);
            return query.list();
        }
    }

    @Override
    public Colour getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Colour colour = session.get(Colour.class, id);
            if(colour==null){
                throw new EntityNotFoundException("Colour", "id", id);
            }
            return colour;
        }
    }

    @Override
    public Colour getByName(String name) {
        try (Session session = sessionFactory.openSession()){
            Query<Colour> query = session.createQuery("from Colour where colour like concat('%', :name, '%')",
                    Colour.class);
            query.setParameter("name", name);
            List<Colour> result = query.list();

            if (result.size()==0){
                throw new EntityNotFoundException("Colour", "name", name);
            }
            return result.get(0);
        }
    }

    @Override
    public Colour create(Colour colour) {
        try (Session session = sessionFactory.openSession()) {
            session.save(colour);
        }
        return colour;
    }

    @Override
    public void delete(int id) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(session.get(Colour.class,id));
            session.getTransaction().commit();
        }
    }
}
