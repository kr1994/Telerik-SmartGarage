package com.java.smart_garage.serviceTest;

import com.java.smart_garage.contracts.repoContracts.ManufacturerRepository;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Manufacturer;
import com.java.smart_garage.services.ManufactureServiceImpl;
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

@ExtendWith(MockitoExtension.class)
public class ManufacturerServiceTest {

    @Mock
    ManufacturerRepository mockRepository;

    @InjectMocks
    ManufactureServiceImpl service;

    @Test
    public void getAllManufacturers_Should_ReturnManufacturers() {
        List<Manufacturer> result = service.getAllManufacturers();
        result.add(createMockManufacturer());

        // Assert
        Mockito.verify(mockRepository, Mockito.timeout(1)).getAllManufacturers();
    }

    @Test
    public void getManufacturerByName_Should_ReturnManufacturerWithThatName() {
        List<Manufacturer> result = new ArrayList<Manufacturer>();
        result.add(service.getByName("BMW"));
        //result.add(createMockManufacturer());

        // Assert
        Mockito.verify(mockRepository, Mockito.timeout(1)).getByName("BMW");
    }

    @Test
    public void getManufacturerById_Should_ReturnManufacturer() {
        // Arrange
        Mockito.when(mockRepository.getById(1)).
                thenReturn(createMockManufacturer());

        //Act
        Manufacturer result = service.getById(1);

        // Assert
        Assertions.assertEquals(1, result.getManufacturerId());
        Assertions.assertEquals("BMW", result.getManufacturerName());
    }


    @Test
    public void create_Should_Throw_When_ManufacturerWithSameNameExists() {
        // Arrange
        var mockManufacturer = createMockManufacturer();
        var mockUser = createMockUser();


        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockManufacturer, mockUser));
    }

    @Test
    public void create_Should_Pass_When_Arguments_Are_True() {
        // Arrange
        var mockManufacturer = createMockManufacturer();
        var mockUser = createMockUser();

        Mockito.when(mockRepository.getByName(mockManufacturer.getManufacturerName())).
                thenThrow(new EntityNotFoundException("Manufacturer", "name", mockManufacturer.getManufacturerName()));


        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.create(mockManufacturer, mockUser));
    }

    @Test
    public void create_Should_Throw_When_ManufacturerWithSameIdExists() {
        // Arrange
        var mockManufacturer = createMockManufacturer();
        var mockUser = createMockUser();


        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockManufacturer, mockUser));
    }

    @Test
    public void create_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockManufacturer = createMockManufacturer();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.create(mockManufacturer, mockUser));
    }


    @Test
    public void delete_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockManufacturer = createMockManufacturer();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.delete(mockManufacturer.getManufacturerId(), mockUser));
    }

    @Test
    public void delete_Should_Pass_When_ManufactureIsDelete() {
        // Arrange
        var mockManufacturer = createMockManufacturer();
        var mockUser = createMockUser();

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.delete(mockManufacturer.getManufacturerId(), mockUser));
    }

}

