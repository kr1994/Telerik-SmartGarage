package com.java.smart_garage.serviceTest;

import com.java.smart_garage.contracts.repoContracts.AutomobileRepository;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.IncorrectPlateRegistrationException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Automobile;
import com.java.smart_garage.services.AutomobileServiceImpl;
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
public class AutomobileServiceTest {

    @Mock
    AutomobileRepository mockRepository;

    @InjectMocks
    AutomobileServiceImpl service;

    @Test
    public void getAllCars_Should_ReturnCars(){
        List<Automobile> result = service.getAllCars();
        result.add(createMockAutomobile());

        // Assert
        Mockito.verify(mockRepository, Mockito.timeout(1)).getAllCars();
    }
    @Test
    public void getAllCarsByOwner_Should_ReturnOwnerCars(){
        List<Automobile> result = service.getAllCarsByOwner(1);
        result.add(createMockAutomobile());

        // Assert
        Mockito.verify(mockRepository, Mockito.timeout(1)).customerId(1);
    }

    @Test
    public void getCarById_Should_ReturnCar(){
        // Arrange
        Mockito.when(mockRepository.getById(1)).
                thenReturn(createMockAutomobile());

        //Act
        Automobile result = service.getById(1);

        // Assert
        Assertions.assertEquals(1, result.getId());
        Assertions.assertEquals("CA2313HM", result.getRegistrationPlate());
        Assertions.assertEquals("Id12345678901234567", result.getIdentifications());
        Assertions.assertEquals("Z1", result.getModel().getModelName());
        Assertions.assertEquals("BMW", result.getModel().getManufacturer().getManufacturerName());
        Assertions.assertEquals("Red", result.getColour().getColour());
        Assertions.assertEquals(2020, result.getYear());
        Assertions.assertEquals("MockEmail@gmail.com", result.getOwner().getPersonalInfo().getEmail());
    }

    @Test
    public void create_Should_Throw_When_CarWithSamePlateExists() {
        // Arrange
        var mockCar = createMockAutomobile();
        var mockUser = createMockUser();

        Mockito.when(mockRepository.getByIdentifications(mockCar.getIdentifications()))
                .thenThrow(new EntityNotFoundException("Car", "identification", mockCar.getIdentifications()));

        Mockito.when(mockRepository.getByPlate(mockCar.getRegistrationPlate()))
                .thenReturn(mockCar);

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockCar,mockUser));
    }

    @Test
    public void create_Should_Throw_When_CarWithSameIdentificationExists() {
        // Arrange
        var mockCar = createMockAutomobile();
        var mockUser = createMockUser();

        Mockito.when(mockRepository.getByIdentifications(mockCar.getIdentifications()))
                .thenReturn(mockCar);

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockCar,mockUser));
    }
    @Test
    public void create_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockCar = createMockAutomobile();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.create(mockCar,mockUser));
    }

    @Test
    public void create_Should_Throw_When_PlateCityIndexIsIncorrect() {
        // Arrange
        var mockCar = createMockAutomobile();
        var mockUser = createMockUser();
        mockCar.setRegistrationPlate("aa1234HM");

        Mockito.when(mockRepository.getByIdentifications(mockCar.getIdentifications()))
                .thenThrow(new EntityNotFoundException("Car", "identification", mockCar.getIdentifications()));

       Mockito.when(mockRepository.getByPlate(mockCar.getRegistrationPlate()))
                .thenThrow(new EntityNotFoundException("Car", "plate", mockCar.getRegistrationPlate()));

        // Act, Assert
        Assertions.assertThrows(IncorrectPlateRegistrationException.class, () -> service.create(mockCar,mockUser));
    }

    @Test
    public void update_Should_Throw_When_CarWithSamePlateExists() {
        // Arrange
        var mockCar = createMockAutomobile();
        var mockUser = createMockUser();

        Mockito.when(mockRepository.getByPlate(mockCar.getRegistrationPlate()))
                .thenReturn(mockCar);

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.update(mockCar,mockUser));
    }

    @Test
    public void update_Should_Throw_When_CarWithSameIdentificationExists() {
        // Arrange
        var mockCar = createMockAutomobile();
        var mockUser = createMockUser();

        Mockito.when(mockRepository.getByIdentifications(mockCar.getIdentifications()))
                .thenReturn(mockCar);

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.update(mockCar,mockUser));
    }
    @Test
    public void update_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockCar = createMockAutomobile();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.update(mockCar,mockUser));
    }

    @Test
    public void delete_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockCar = createMockAutomobile();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.delete(mockCar.getId(),mockUser));
    }

    @Test
    public void delete_Should_Pass_When_CarIsDelete() {
        // Arrange
        var mockCar = createMockAutomobile();
        var mockUser = createMockUser();


        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.delete(mockCar.getId(),mockUser));
    }

}
