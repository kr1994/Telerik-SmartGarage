package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.CarServiceRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.Automobile;
import com.java.smart_garage.models.CarService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

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
    public List<CarService> getAllCarServicesByCustomer(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<CarService> query = session.createQuery("from CarService cs where cs.automobile.id  = :id order by carServicesId", CarService.class);
            query.setParameter("id", id);
            return query.list();
        }
    }

    @Override
    public List<CarService> getAllCarServicesByCar(int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<CarService> query = session.createQuery("from CarService cs where cs.automobile.id = :id order by carServicesId", CarService.class);
            query.setParameter("id", id);
            return query.list();
        }
    }
    @Override
    public double getCarServicesPrice(int id) {
        try (Session session = sessionFactory.openSession()) {
            double price = 0;
            Query<CarService> query = session.createQuery("from CarService cs where cs.automobile.id  = :id order by carServicesId", CarService.class);
            query.setParameter("id", id);
            List<CarService> carServices = query.list();
            for (CarService carService : carServices) {
                price = price + carService.getService().getWorkServicePrice();
            }
            return price;
        }
    }

    public List<CarService> filterByDateAndCarId(Optional<Date> startingDate, Optional<Date> endingDate,int id) {
        try (Session session = sessionFactory.openSession()) {
            Query<CarService> query = null;

            if ((startingDate.isPresent()) && (endingDate.isPresent())) {
                query = session.createQuery("from CarService cs where invoice.date>= :startingDate and invoice.date<= :endingDate and  cs.automobile.id  = :id ",CarService.class);
                query.setParameter("startingDate", startingDate.get());
                query.setParameter("endingDate", endingDate.get());
                query.setParameter("id", id);
            } else if ((startingDate.isPresent())) {
                query = session.createQuery("from CarService cs where invoice.date >= :startingDate and cs.automobile.id  = :id", CarService.class);
                query.setParameter("startingDate", startingDate.get());
                query.setParameter("id", id);
            } else if ((endingDate.isPresent())) {
                query = session.createQuery("from CarService cs where invoice.date<= :endingDate and cs.automobile.id  = :id", CarService.class);
                query.setParameter("endingDate", endingDate.get());
                query.setParameter("id", id);
            } else {
               return getAllCarServicesByCar(id);
            }
            return query.list();
        }
    }


    @Override
    public CarService getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            CarService carService = session.get(CarService.class, id);
            if (carService == null){
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



    @Override
    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(session.get(Automobile.class, id));
            session.getTransaction().commit();
        }
    }
}
