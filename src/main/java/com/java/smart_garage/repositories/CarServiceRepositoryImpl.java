package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.CarServiceRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.CarService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarServiceRepositoryImpl implements CarServiceRepository {
    private final SessionFactory sessionFactory;
    @Autowired
    public CarServiceRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<CarService> getAllCarServices() {
        try (Session session = sessionFactory.openSession()) {
            Query<CarService> query = session.createQuery("from CarService order by carServicesId", CarService.class);
            return query.list();
        }
    }


    @Override
    public CarService getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            CarService carService = session.get(CarService.class, id);
            if(carService==null){
                throw new EntityNotFoundException("CarService", "id", id);
            }
            return carService;
        }
    }



    @Override
    public CarService create(CarService carService) {
        try (Session session = sessionFactory.openSession()) {
            session.save(carService);
        }
        return carService;
    }
}
