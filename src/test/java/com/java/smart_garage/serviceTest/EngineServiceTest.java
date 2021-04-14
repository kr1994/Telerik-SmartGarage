package com.java.smart_garage.serviceTest;

import com.java.smart_garage.contracts.repoContracts.EngineRepository;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Engine;
import com.java.smart_garage.services.EngineServiceImpl;
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
public class EngineServiceTest {

    @Mock
    EngineRepository mockEngineRepository;

    @InjectMocks
    EngineServiceImpl service;

    @Test
    public void getAllEngines_Should_Return_AllEngines() {
        List<Engine> result = service.getAllEngines();
        result.add(createMockEngine());

        // Assert
        Mockito.verify(mockEngineRepository, Mockito.timeout(1)).getAllEngines();
    }

    @Test
    public void getById_Should_Return_Engine() {
        // Arrange
        Mockito.when(mockEngineRepository.getById(1)).
                thenReturn(createMockEngine());

        //Act
        Engine result = service.getById(1);

        // Assert
        Assertions.assertEquals(1, result.getEngineId());

    }


    @Test
    public void create_Should_Throw_When_EngineExists() {
        // Arrange
        var mockEngine = createMockEngine();
        var mockUser = createMockUser();

        Mockito.when(mockEngineRepository.getById(mockEngine.getEngineId())).
                thenReturn(createMockEngine());

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockEngine, mockUser));
    }

    @Test
    public void create_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockEngine = createMockEngine();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.create(mockEngine, mockUser));
    }

    @Test
    public void create_Should_Pass_When_Engine() {
        // Arrange
        var mockEngine = createMockEngine();
        var mockUser = createMockUser();

        Mockito.when(mockEngineRepository.getById(mockEngine.getEngineId()))
                .thenThrow(new EntityNotFoundException("Credential", "id",mockEngine.getEngineId()));

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.create(mockEngine, mockUser));
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
    public void delete_Should_Pass_When_EngineIsAvailable() {
        // Arrange
        var mockEngine = createMockEngine();
        var mockUser = createMockUser();


        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.delete(mockEngine.getEngineId(),mockUser));
    }
}
