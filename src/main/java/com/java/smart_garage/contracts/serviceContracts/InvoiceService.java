package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Invoice;
import com.java.smart_garage.models.Credential;
import com.java.smart_garage.models.User;

import java.util.Date;
import java.util.List;

public interface InvoiceService {
    List<Invoice> getAllInvoices();

    Invoice getById(int id);

    List<Invoice> getByCustomer(int customerId);

    Invoice getByDate(Date date);

    void create(Invoice invoice, User user);

    void delete(int id, User user);
}
