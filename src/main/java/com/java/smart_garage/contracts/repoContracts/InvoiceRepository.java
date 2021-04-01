package com.java.smart_garage.contracts.repoContracts;

import com.java.smart_garage.models.Invoice;

import java.util.List;

public interface InvoiceRepository {
    List<Invoice> getAllInvoices();

    Invoice getById(int id);

    List<Invoice> getInvoiceByCustomer(int customerId);

    Invoice create(Invoice invoice);

    void delete(int id);
}
