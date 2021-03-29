package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.YearRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.Year;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class YearRepositoryImpl implements YearRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public YearRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Year> getAllYears() {
        try (Session session = sessionFactory.openSession()) {
            Query<Year> query = session.createQuery("from Year order by yearId", Year.class);
            return query.list();
        }
    }

    @Override
    public Year getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Year year = session.get(Year.class, id);
            if(year==null){
                throw new EntityNotFoundException("Year", "id", id);
            }
            return year;
        }
    }

    public Year getByYear(int year){
        try(Session session = sessionFactory.openSession()){
            Query<Year> query = session.createQuery("from Year where year = :year", Year.class);
            query.setParameter("year", year);
            List<Year> result = query.list();

            if (result.size()==0){
                throw new EntityNotFoundException(year);
            }
            return result.get(0);
        }
    }

    @Override
    public Year create(Year year) {
        try (Session session = sessionFactory.openSession()) {
            session.save(year);
        }
        return year;
    }

    @Override
    public void delete(int id) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(session.get(Year.class,id));
            session.getTransaction().commit();
        }
    }
}
