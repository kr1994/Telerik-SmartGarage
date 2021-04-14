package com.java.smart_garage.serviceTest;

import com.java.smart_garage.contracts.repoContracts.FuelRepository;
import com.java.smart_garage.contracts.repoContracts.InvoiceRepository;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Fuel;
import com.java.smart_garage.models.Invoice;
import com.java.smart_garage.models.User;
import com.java.smart_garage.services.FuelServiceImpl;
import com.java.smart_garage.services.InvoiceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.java.smart_garage.Helpers.*;
import static com.java.smart_garage.Helpers.createMockUser;

@ExtendWith(MockitoExtension.class)
public class InvoiceServiceTest {

    @Mock
    InvoiceRepository mockInvoiceRepository;

    @InjectMocks
    InvoiceServiceImpl service;

    @Test
    public void getAllInvoice_Should_Return_AllInvoice() {
        List<Invoice> result = service.getAllInvoices();
        result.add(createMockInvoice());

        // Assert
        Mockito.verify(mockInvoiceRepository, Mockito.timeout(1)).getAllInvoices();
    }

    @Test
    public void getById_Should_Return_Invoice() {
        // Arrange
        Mockito.when(mockInvoiceRepository.getById(1)).
                thenReturn(createMockInvoice());

        //Act
        Invoice result = service.getById(1);

        // Assert
        Assertions.assertEquals(1, result.getInvoiceId());

    }

    @Test
    public void getByCustomer_Should_ReturnCustomerInvoice() {
        // Arrange
        List<Invoice> result = service.getByCustomer(1);
        result.add(createMockInvoice());

        // Assert
        Mockito.verify(mockInvoiceRepository, Mockito.timeout(1)).getInvoiceByCustomer(1);


    }

    @Test
    public void create_Should_Throw_When_InvoiceExists() {
        // Arrange
        var mockInvoice = createMockInvoice();
        var mockUser = createMockUser();

        Mockito.when(mockInvoiceRepository.getById(mockInvoice.getInvoiceId())).
                thenReturn(createMockInvoice());

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockInvoice, mockUser));
    }

    @Test
    public void create_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockInvoice = createMockInvoice();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.create(mockInvoice, mockUser));
    }

    @Test
    public void create_Should_Pass_When_Invoice() {
        // Arrange
        var mockInvoice = createMockInvoice();
        var mockUser = createMockUser();

        Mockito.when(mockInvoiceRepository.getById(mockInvoice.getInvoiceId()))
                .thenThrow(new EntityNotFoundException("Invoice", "id",mockInvoice.getInvoiceId()));

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.create(mockInvoice, mockUser));
    }
    @Test
    public void delete_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockInvoice = createMockInvoice();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.delete(mockInvoice.getInvoiceId(),mockUser));
    }

    @Test
    public void delete_Should_Pass_And_delete_Invoice() {
        // Arrange
        var mockInvoice = createMockInvoice();
        var mockUser = createMockUser();


        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.delete(mockInvoice.getInvoiceId(),mockUser));
    }
}
