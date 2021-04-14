package com.java.smart_garage.serviceTest;

import com.java.smart_garage.contracts.repoContracts.UserTypeRepository;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.UserType;
import com.java.smart_garage.services.UserTypeServiceImpl;
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
public class UserTypeServiceTest {
    @Mock
    UserTypeRepository mockUserTypeRepository;

    @InjectMocks
    UserTypeServiceImpl service;

    @Test
    public void getAllUserTypes_Should_Return_AllUserTypes() {
        List<UserType> result = service.getAllTypes();
        result.add(createMockUserTypeCustomer());

        // Assert
        Mockito.verify(mockUserTypeRepository, Mockito.timeout(1)).getAllUserTypes();
    }

    @Test
    public void getById_Should_Return_UserType() {
        // Arrange
        Mockito.when(mockUserTypeRepository.getById(1)).
                thenReturn(createMockUserTypeCustomer());

        //Act
        UserType result = service.getById(1);

        // Assert
        Assertions.assertEquals(1, result.getTypeId());

    }

    @Test
    public void getByName_Should_ReturnUserType() {
        // Arrange
        Mockito.when(mockUserTypeRepository.getByName("Customer")).
                thenReturn(createMockUserTypeCustomer());

        //Act
        UserType result = service.getByName("Customer");

        // Assert
        Assertions.assertEquals("Customer", result.getType());

    }

    @Test
    public void create_Should_Throw_When_UserTypeExists() {
        // Arrange
        var mockUserType = createMockUserTypeCustomer();
        var mockUser = createMockUser();

        Mockito.when(mockUserTypeRepository.getByName(mockUserType.getType())).
                thenReturn(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockUserType, mockUser));
    }

    @Test
    public void create_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockUserType = createMockUserTypeCustomer();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.create(mockUserType, mockUser));
    }

    @Test
    public void create_Should_Pass_When_UserType() {
        // Arrange
        var mockUserType = createMockUserTypeCustomer();
        var mockUser = createMockUser();

        Mockito.when(mockUserTypeRepository.getByName(mockUserType.getType()))
                .thenThrow(new EntityNotFoundException("User Type", "type",mockUserType.getType()));

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.create(mockUserType, mockUser));
    }
    @Test
    public void delete_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockUserType = createMockUserTypeCustomer();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class,
                () -> service.delete(mockUserType.getTypeId(),mockUser));
    }

    @Test
    public void delete_Should_Pass_And_delete_UserType() {
        // Arrange
        var mockUserType = createMockUserTypeCustomer();
        var mockUser = createMockUser();

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.delete(mockUserType.getTypeId(),mockUser));
    }
}

