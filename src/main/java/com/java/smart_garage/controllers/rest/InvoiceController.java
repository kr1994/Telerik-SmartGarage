package com.java.smart_garage.controllers.rest;


import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.contracts.serviceContracts.InvoiceService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.models.Invoice;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.dto.InvoiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("smartgarage/invoices")
public class InvoiceController {

    private final InvoiceService service;
    private final ModelConversionHelper modelConversionHelper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public InvoiceController(InvoiceService service,
                             ModelConversionHelper modelConversionHelper,
                             AuthenticationHelper authenticationHelper) {
        this.service = service;
        this.modelConversionHelper = modelConversionHelper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Invoice> getAllInvoice(){
        return service.getAllInvoices();
    }
    @GetMapping("/{id}")
    public Invoice getById(@PathVariable int id) {
        try {
            return service.getById(id);
        }
        catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public Invoice create(@RequestHeader HttpHeaders headers, @Valid @RequestBody InvoiceDto invoiceDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Invoice invoice = modelConversionHelper.invoiceFromDto(invoiceDto);
            service.create(invoice, user);
            return invoice;
        } catch (DuplicateEntityException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

}
