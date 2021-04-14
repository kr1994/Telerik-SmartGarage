package com.java.smart_garage.serviceTest;

import com.java.smart_garage.contracts.repoContracts.UserRepository;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.User;
import com.java.smart_garage.services.UserServiceImpl;
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
import static com.java.smart_garage.Helpers.createMockUser;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository mockUserRepository;

    @InjectMocks
    UserServiceImpl service;

    @Test
    public void getAllUsers_Should_Return_AllUsers() {
        List<User> result = service.getAllUsers();
        result.add(createMockUser());

        // Assert
        Mockito.verify(mockUserRepository, Mockito.timeout(1)).getAllUsers();
    }

    @Test
    public void getById_Should_Return_Correct_User() {
        // Arrange
        Mockito.when(mockUserRepository.getById(1)).
                thenReturn(createMockUser());

        //Act
        User result = service.getById(1);

        // Assert
        Assertions.assertEquals(1, result.getUserId());
    }

    @Test
    public void create_Should_Throw_When_User_Exists() {
        // Arrange
        var mockUserEmployee = createMockUser();

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockUserEmployee));
    }

    @Test
    public void create_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockUserCustomer = createMockUser();
        mockUserCustomer.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.create(mockUserCustomer));
    }

    @Test
    public void create_Should_Pass_When_Put_Correct_User() {
        // Arrange
        var mockUserEmployee = createMockUser();

        Mockito.when(mockUserRepository.getById(mockUserEmployee.getUserId()))
                .thenThrow(new EntityNotFoundException("User", "id", mockUserEmployee.getUserId()));

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.create(mockUserEmployee));
    }

    @Test
    public void update_Should_Pass_When_Put_Correct_User() {
        // Arrange
        var mockUserEmployee = createMockUser();

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.update(mockUserEmployee));
    }

    @Test
    public void update_Should_Throw_When_Put_Not_Existing_Id() {
        // Arrange
        var mockUserEmployee = createMockUser();

        Mockito.when(mockUserRepository.getById(mockUserEmployee.getUserId()))
                .thenThrow(new EntityNotFoundException("User", "id", mockUserEmployee.getUserId()));

        // Act, Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.update(mockUserEmployee));
    }

    @Test
    public void update_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockUserCustomer = createMockUser();
        mockUserCustomer.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.update(mockUserCustomer));
    }

    @Test
    public void delete_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockUserCustomer = createMockUser();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.delete(mockUserCustomer.getUserId(), mockUser));
    }

    @Test
    public void delete_Should_Pass_When_User_Exists() {
        // Arrange
        var mockUserCustomer = createMockUser();
        mockUserCustomer.setUserType(createMockUserTypeCustomer());
        var mockUser = createMockUser();

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.delete(mockUserCustomer.getUserId(),mockUser));
    }

    @Test
    public void filter_Should_Pass_When_Put_Correct_Data() {
        // Arrange
        var mockUserEmployee = createMockUser();

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.filterCustomers(Optional.empty(), Optional.of("Georgiev"),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), mockUserEmployee));
    }

    @Test
    public void filter_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockUserCustomer = createMockUser();
        mockUserCustomer.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.filterCustomers(Optional.empty(), Optional.of("Georgiev"),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), mockUserCustomer));
    }

    @Test
    public void sortByName_Should_Pass_When_Put_Correct_Data() {
        // Arrange
        var mockUserEmployee = createMockUser();

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.sortCustomersByName(true, mockUserEmployee));
    }

    @Test
    public void sortByName_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockUserCustomer = createMockUser();
        mockUserCustomer.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.sortCustomersByName(false, mockUserCustomer));
    }

    @Test
    public void sortByVisits_Should_Pass_When_Put_Correct_Data() {
        // Arrange
        var mockUserEmployee = createMockUser();

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.sortCustomersByVisits(false, mockUserEmployee));
    }

    @Test
    public void sortByVisits_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockUserCustomer = createMockUser();
        mockUserCustomer.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.sortCustomersByVisits(true, mockUserCustomer));
    }

}



