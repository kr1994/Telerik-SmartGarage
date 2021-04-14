package com.java.smart_garage.serviceTest;

import com.java.smart_garage.contracts.repoContracts.FuelRepository;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Credential;
import com.java.smart_garage.models.Fuel;
import com.java.smart_garage.services.FuelServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.java.smart_garage.Helpers.*;

@ExtendWith(MockitoExtension.class)
public class FuelServiceTest {
    @Mock
    FuelRepository mockFuelRepository;

    @InjectMocks
    FuelServiceImpl service;

    @Test
    public void getAllFuels_Should_Return_AllFuels() {
        List<Fuel> result = service.getAllFuels();
        result.add(createMockFuel());

        // Assert
        Mockito.verify(mockFuelRepository, Mockito.timeout(1)).getAllFuels();
    }

    @Test
    public void getById_Should_Return_Fuel() {
        // Arrange
        Mockito.when(mockFuelRepository.getById(1)).
                thenReturn(createMockFuel());

        //Act
        Fuel result = service.getById(1);

        // Assert
        Assertions.assertEquals(1, result.getFuelId());

    }

    @Test
    public void getByName_Should_ReturnFuelName() {
        // Arrange
        Mockito.when(mockFuelRepository.getByName("Gas")).
                thenReturn(createMockFuel());

        //Act
        Fuel result = service.getByName("Gas");

        // Assert
        Assertions.assertEquals("Gas", result.getFuelName());


    }

    @Test
    public void create_Should_Throw_When_FuelExists() {
        // Arrange
        var mockFuel = createMockFuel();
        var mockUser = createMockUser();

        Mockito.when(mockFuelRepository.getByName(mockFuel.getFuelName())).
                thenReturn(createMockFuel());

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockFuel, mockUser));
    }

    @Test
    public void create_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockFuel = createMockFuel();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.create(mockFuel, mockUser));
    }

    @Test
    public void create_Should_Pass_When_Fuel() {
        // Arrange
        var mockFuel = createMockFuel();
        var mockUser = createMockUser();

        Mockito.when(mockFuelRepository.getByName(mockFuel.getFuelName()))
                .thenThrow(new EntityNotFoundException("Fuel", "name",mockFuel.getFuelName()));

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.create(mockFuel, mockUser));
    }
    @Test
    public void delete_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockFuel = createMockFuel();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.delete(mockFuel.getFuelId(),mockUser));
    }

    @Test
    public void delete_Should_Pass_And_delete_Fuel() {
        // Arrange
        var mockFuel = createMockFuel();
        var mockUser = createMockUser();


        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.delete(mockFuel.getFuelId(),mockUser));
    }
}
