package com.java.smart_garage.repositories;


import com.java.smart_garage.contracts.repoContracts.CityRepository;
import com.java.smart_garage.models.City;
import com.java.smart_garage.models.PersonalInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class CityRepositoryImp implements CityRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public CityRepositoryImp(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<City> getAllCityIndex() {

        try (Session session = sessionFactory.openSession()) {
            Query<City> query = session.createQuery(
                    "from City ", City.class);

            return query.list();
        }
    }

}
