package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.FuelRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.Fuel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class FuelRepositoryImpl implements FuelRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public FuelRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Fuel> getAllFuels() {
        try (Session session = sessionFactory.openSession()) {
            Query<Fuel> query = session.createQuery("from Fuel order by fuelId", Fuel.class);
            return query.list();
        }

    }

    @Override
    public Fuel getById(int id){
        try (Session session = sessionFactory.openSession()) {
            Fuel fuel = session.get(Fuel.class, id);
            if(fuel==null){
                throw new EntityNotFoundException("Fuel", "id", id);
            }
            return fuel;
        }
    }

    @Override
    public Fuel getByName(String name){
        try (Session session = sessionFactory.openSession()){
            Query<Fuel> query = session.createQuery("from Fuel where fuelName like concat('%', :name, '%')",
                    Fuel.class);
            query.setParameter("name", name);
            List<Fuel> result = query.list();

            if (result.size()==0){
                throw new EntityNotFoundException("Fuel", "name", name);
            }
            return result.get(0);
        }
    }

    @Override
    public Fuel create(Fuel fuel){
        try (Session session = sessionFactory.openSession()) {
            session.save(fuel);
        }
        return fuel;
    }


    @Override
    public void delete(int id){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(session.get(Fuel.class,id));
            session.getTransaction().commit();
        }
    }
}
