package com.java.smart_garage.serviceTest;

import com.java.smart_garage.contracts.repoContracts.CarServiceRepository;
import com.java.smart_garage.contracts.serviceContracts.CurrencyMultiplierService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.CarService;
import com.java.smart_garage.services.CarServiceServiceImpl;
import com.java.smart_garage.services.CurrencyMultiplierServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.java.smart_garage.Helpers.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceServiceTest {

    @Mock
    CarServiceRepository mockRepository;

    @Mock
    CurrencyMultiplierService multiplierService;

    @InjectMocks
    CarServiceServiceImpl service;


    @Test
    public void getAllCarServices_Should_AllCarServices() {
        List<CarService> result = service.getAllCarServices();
        result.add(createMockCarService());

        // Assert
        Mockito.verify(mockRepository, Mockito.timeout(1)).getAllCarServices();
    }

    @Test
    public void getCarServiceById_Should_ReturnCarService() {
        // Arrange
        Mockito.when(mockRepository.getById(1)).
                thenReturn(createMockCarService());

        //Act
        CarService result = service.getById(1);

        // Assert
        Assertions.assertEquals(1, result.getCarServicesId());
        Assertions.assertEquals(150, result.getService().getWorkServicePrice());
        Assertions.assertEquals("CA2313HM", result.getCar().getRegistrationPlate());

    }

    @Test
    public void getAllCarServicesByCustomer_Should_ReturnCarService() {
        var mockUser = createMockUser();

        List<CarService> result = service.getAllCarServicesByCustomer(mockUser.getUserId());
        result.add(createMockCarService());

        // Assert
        Mockito.verify(mockRepository, Mockito.timeout(1)).getAllCarServicesByCustomer(mockUser.getUserId());
    }

    @Test
    public void getAllCarServicesByCar_Should_ReturnCarService() {
        var mockCar = createMockAutomobile();

        List<CarService> result = service.getAllCarServicesByCar(mockCar.getId());
        result.add(createMockCarService());

        // Assert
        Mockito.verify(mockRepository, Mockito.timeout(1)).getAllCarServicesByCar(mockCar.getId());
    }

    @Test
    public void getCarServicesPrice_Should_ReturnCarServicePrice() {
        var mockCarService = createMockCarService();

        double result = service.getCarServicesPrice(mockCarService.getService().getWorkServiceId());

        // Assert
        Mockito.verify(mockRepository, Mockito.timeout(1)).getCarServicesPrice((mockCarService.getCarServicesId()));

    }

    @Test
    public void create_Should_Throw_When_CarServiceExists() {
        // Arrange
        var mockCarService = createMockCarService();
        var mockUser = createMockUser();

        Mockito.when(mockRepository.getById(mockCarService.getCarServicesId()))
                .thenReturn(mockCarService);

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockCarService, mockUser));
    }

    @Test
    public void create_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockCarService = createMockCarService();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.create(mockCarService, mockUser));
    }

    @Test
    public void create_Should_Pass_When_CreatCarService() {
        // Arrange
        var mockCarService = createMockCarService();
        var mockUser = createMockUser();

        Mockito.when(mockRepository.getById(mockCarService.getCarServicesId()))
                .thenThrow(new EntityNotFoundException("CarService", "id", mockCarService.getCarServicesId()));

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.create(mockCarService, mockUser));
    }

    @Test
    public void delete_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockCarService = createMockCarService();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.delete(mockCarService.getCarServicesId(),mockUser));
    }

    @Test
    public void delete_Should_Pass_When_CarService() {
        // Arrange
        var mockCarService = createMockCarService();
        var mockUser = createMockUser();


        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.delete(mockCarService.getCarServicesId(),mockUser));
    }

    @Test
    public void getAllCarServicesByView_Should_CarServicesByView() {
        // Arrange
        var mockCarService = createMockCarService();


        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.getAllCarServicesByView(Optional.empty(), Optional.empty(),mockCarService.getCarServicesId(), Optional.empty()));
    }
    @Test
    public void getAllCarServicesByView_Should_CarServicesByViewWithCurrency() throws Exception {
        // Arrange
        var mockCarService = createMockCarService();
        Optional<String> currency = Optional.of("BGN");
        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.getAllCarServicesByView(Optional.empty(), Optional.empty(),mockCarService.getCarServicesId(),currency));
    }

}


