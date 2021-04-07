package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Invoice;
import com.java.smart_garage.models.Credential;

import java.util.List;

public interface InvoiceService {
    List<Invoice> getAllInvoices();

    Invoice getById(int id);

    List<Invoice> getByCustomer(int customerId);

    void create(Invoice invoice, Credential credential);

    void delete(int id, Credential credential);
}
