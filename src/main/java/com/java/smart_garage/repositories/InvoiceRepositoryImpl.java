package com.java.smart_garage.repositories;

import com.java.smart_garage.contracts.repoContracts.InvoiceRepository;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.Invoice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class InvoiceRepositoryImpl implements InvoiceRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public InvoiceRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        try (Session session = sessionFactory.openSession()) {
            Query<Invoice> query = session.createQuery("from Invoice i where exists (from CarService cs where cs.invoice = i) order by invoiceId",
                    Invoice.class);
            return query.list();
        }
    }

    @Override
    public Invoice getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Invoice invoice = session.get(Invoice.class, id);
            if (invoice == null) {
                throw new EntityNotFoundException("Invoice", "id", id);
            }
            return invoice;
        }
    }

    @Override
    public List<Invoice> getInvoiceByCustomer(int customerId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Invoice> query = session.createQuery("from Invoice i where exists (from CarService cs where cs.automobile.user.id = :id and cs.invoice = i )", Invoice.class);
            query.setParameter("id", customerId);
            return query.list();
        }

    }
    public Invoice getByDate(Date date) {
        try (Session session = sessionFactory.openSession()) {
            Query<Invoice> query = session.createQuery("from Invoice  where date = :date ", Invoice.class);
            query.setParameter("date", date);
            return query.list().get(0);
        }
    }

    @Override
    public Invoice create(Invoice invoice){
        try (Session session = sessionFactory.openSession()) {
            session.save(invoice);
        }
        return invoice;
    }


    @Override
    public void delete(int id){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(session.get(Invoice.class,id));
            session.getTransaction().commit();
        }
    }

}

