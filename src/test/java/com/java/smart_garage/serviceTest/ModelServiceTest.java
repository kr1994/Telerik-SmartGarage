package com.java.smart_garage.serviceTest;

import com.java.smart_garage.contracts.repoContracts.ModelRepository;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Model;
import com.java.smart_garage.services.ModelServiceImpl;
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
public class ModelServiceTest {

    @Mock
    ModelRepository mockModelRepository;

    @InjectMocks
    ModelServiceImpl service;

    @Test
    public void getAllModel_Should_Return_AllModels() {
        List<Model> result = service.getAllModels();
        result.add(createMockModel());

        // Assert
        Mockito.verify(mockModelRepository, Mockito.timeout(1)).getAllModels();
    }

    @Test
    public void getById_Should_ReturnModel() {
        // Arrange
        Mockito.when(mockModelRepository.getById(1)).
                thenReturn(createMockModel());

        //Act
        Model result = service.getModelById(1);

        // Assert
        Assertions.assertEquals(1, result.getModelId());
        Assertions.assertEquals("Z1", result.getModelName());

    }

    @Test
    public void getByName_Should_ReturnThatModel() {
        // Arrange
        Mockito.when(mockModelRepository.getByName("Z1")).
                thenReturn(createMockModel());

        //Act
        Model result = service.getModelByName("Z1");

        // Assert
        Assertions.assertEquals(1, result.getModelId());
        Assertions.assertEquals("Z1", result.getModelName());

    }

    @Test
    public void create_Should_Throw_When_ModelExists() {
        // Arrange
        var mockModel = createMockModel();
        var mockUser = createMockUser();

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockModel, mockUser));
    }

    @Test
    public void create_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockModel = createMockModel();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.create(mockModel, mockUser));
    }

    @Test
    public void create_Should_Pass_When_CreateModel() {
        // Arrange
        var mockModel = createMockModel();
        var mockUser = createMockUser();

        Mockito.when(mockModelRepository.getByName(mockModel.getModelName()))
                .thenThrow(new EntityNotFoundException("Model", "id", mockModel.getModelId()));

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.create(mockModel, mockUser));
    }

    @Test
    public void delete_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockModel = createMockModel();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () ->
                service.delete(mockModel.getModelId(), mockUser));
    }

    @Test
    public void delete_Should_Pass_When_Model() {
        // Arrange
        var mockModel = createMockModel();
        var mockUser = createMockUser();


        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.delete(mockModel.getModelId(),mockUser));
    }
}

