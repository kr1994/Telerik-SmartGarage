package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.ManufacturerRepository;
import com.java.smart_garage.models.Manufacturer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.java.smart_garage.exceptions.EntityNotFoundException;

import java.util.List;

@Repository
public class ManufacturerRepositoryImpl implements ManufacturerRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ManufacturerRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Manufacturer> getAllManufacturers() {
        try (Session session = sessionFactory.openSession()) {
            Query<Manufacturer> query = session.createQuery("from Manufacturer order by manufacturerId", Manufacturer.class);
            return query.list();
        }

    }
    @Override
    public Manufacturer getById(int id){
        try (Session session = sessionFactory.openSession()) {
            Manufacturer manufacturer = session.get(Manufacturer.class, id);
            if(manufacturer==null){
                throw new EntityNotFoundException("Category", "id", id);
            }
            return manufacturer;
        }
    }
    @Override
    public Manufacturer getByName(String name){
        try (Session session = sessionFactory.openSession()){
            Query<Manufacturer> query = session.createQuery("from Manufacturer where manufacturerName like concat('%', :name, '%')",
                     Manufacturer.class);
            query.setParameter("name", name);
            List<Manufacturer> result = query.list();

            if (result.size()==0){
                throw new EntityNotFoundException("Manufacturer", "name", name);
            }
            return result.get(0);
        }
    }
    @Override
    public Manufacturer create(Manufacturer manufacturer){
       try (Session session = sessionFactory.openSession()) {
            session.save(manufacturer);
        }
       return manufacturer;
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
