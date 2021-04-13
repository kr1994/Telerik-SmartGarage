package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.CurrencyRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.Currency;
import com.java.smart_garage.models.Model;
import com.java.smart_garage.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CurrencyRepositoryImpl implements CurrencyRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public CurrencyRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Currency getCurrencyByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Currency> query = session.createQuery("from Currency where name like concat('%', :name, '%')",
                    Currency.class);
            query.setParameter("name", name);
            List<Currency> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Currency", "name", name);
            }
            return result.get(0);
        }
    }

}
