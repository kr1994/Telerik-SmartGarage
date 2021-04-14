package com.java.smart_garage.serviceTest;


import com.java.smart_garage.contracts.repoContracts.CredentialRepository;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Credential;
import com.java.smart_garage.services.CredentialServiceImpl;
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
public class CredentialServiceTest {

    @Mock
    CredentialRepository mockCredentialRepository;

    @InjectMocks
    CredentialServiceImpl service;

    @Test
    public void getAllCredentials_Should_Return_AllCredentials() {
        List<Credential> result = service.getAllCredentials();
        result.add(createMockCredential());

        // Assert
        Mockito.verify(mockCredentialRepository, Mockito.timeout(1)).getAllCredentials();
    }

    @Test
    public void getById_Should_Return_Credential() {
        // Arrange
        Mockito.when(mockCredentialRepository.getById(1)).
                thenReturn(createMockCredential());

        //Act
        Credential result = service.getById(1);

        // Assert
        Assertions.assertEquals("MockUser", result.getUsername());
        Assertions.assertEquals(1, result.getCredentialId());

    }

    @Test
    public void getByUserName_Should_ReturnCredentialUsername() {
        // Arrange
        Mockito.when(mockCredentialRepository.getByUsername("MockUser")).
                thenReturn(createMockCredential());

        //Act
        Credential result = service.getByUsername("MockUser");

        // Assert
        Assertions.assertEquals("MockUser", result.getUsername());
        Assertions.assertEquals(1, result.getCredentialId());

    }
    @Test
    public void update_Should_Throw_When_CredentialExists() {
        // Arrange
        var mockCredential = createMockCredential();
        var mockUser = createMockUser();

        Mockito.when(mockCredentialRepository.getByUsername(mockCredential.getUsername())).
                thenReturn(createMockCredential());

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.update(mockCredential, mockUser));
    }

    @Test
    public void update_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockCredential = createMockCredential();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.update(mockCredential, mockUser));
    }

    @Test
    public void update_Should_Pass_When_Credential() {
        // Arrange
        var mockCredential = createMockCredential();
        var mockUser = createMockUser();

        Mockito.when(mockCredentialRepository.getByUsername(mockCredential.getUsername()))
                .thenThrow(new EntityNotFoundException("Credential", "username",mockCredential.getUsername()));

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.update(mockCredential, mockUser));
    }

    @Test
    public void create_Should_Throw_When_CredentialExists() {
        // Arrange
        var mockCredential = createMockCredential();
        var mockUser = createMockUser();

        Mockito.when(mockCredentialRepository.getByUsername(mockCredential.getUsername())).
                thenReturn(createMockCredential());

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockCredential, mockUser));
    }

    @Test
    public void create_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockCredential = createMockCredential();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.create(mockCredential, mockUser));
    }

    @Test
    public void create_Should_Pass_When_Credential() {
        // Arrange
        var mockCredential = createMockCredential();
        var mockUser = createMockUser();

        Mockito.when(mockCredentialRepository.getByUsername(mockCredential.getUsername()))
                .thenThrow(new EntityNotFoundException("Credential", "username",mockCredential.getUsername()));

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.create(mockCredential, mockUser));
    }
    @Test
    public void delete_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockCredential = createMockCredential();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.delete(mockCredential.getCredentialId(),mockUser));
    }

    @Test
    public void delete_Should_Pass_When_CarService() {
        // Arrange
        var mockCredential = createMockCredential();
        var mockUser = createMockUser();


        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.delete(mockCredential.getCredentialId(),mockUser));
    }
}
