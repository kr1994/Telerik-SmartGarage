package com.java.smart_garage.contracts.serviceContracts;

import com.java.smart_garage.models.Invoice;
import com.java.smart_garage.models.User;

import java.util.List;

public interface InvoiceService {
    List<Invoice> getAllFuels();

    Invoice getById(int id);

    List<Invoice> getByCustomer(int customerId);

    void create(Invoice invoice, User user);

    void delete(int id, User user);
}
