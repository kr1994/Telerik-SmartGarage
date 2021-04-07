package com.java.smart_garage.services;


import com.java.smart_garage.contracts.repoContracts.InvoiceRepository;
import com.java.smart_garage.contracts.serviceContracts.InvoiceService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Invoice;
import com.java.smart_garage.models.Credential;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository repository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return repository.getAllInvoices();
    }

    @Override
    public Invoice getById(int id) {
        return repository.getById(id);
    }

    @Override
    public List<Invoice> getByCustomer(int customerId) {
        return repository.getInvoiceByCustomer(customerId);
    }

    @Override
    public void create(Invoice invoice, User user) {
        boolean duplicateExists = true;

        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee can create a new fuel.");
        }

        try {
            repository.getById(invoice.getInvoiceId());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new DuplicateEntityException("Invoice", "id", invoice.getInvoiceId());
        }

        repository.create(invoice);
    }

    @Override
    public void delete(int id, User user) {
        if (!(user.isEmployee())) {
            throw new UnauthorizedOperationException("Only employee  can delete a fuel.");
        }
        Invoice invoice = new Invoice();
        try {
            invoice = repository.getById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Invoice", "id", id);
        }
        repository.delete(id);
    }

}
