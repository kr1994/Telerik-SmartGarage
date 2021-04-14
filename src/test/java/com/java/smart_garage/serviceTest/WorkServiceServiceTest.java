package com.java.smart_garage.serviceTest;

import com.java.smart_garage.contracts.repoContracts.WorkServiceRepository;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.WorkService;
import com.java.smart_garage.services.WorkServiceServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.java.smart_garage.Helpers.*;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class WorkServiceServiceTest {

    @Mock
    WorkServiceRepository mockWorkServiceRepository;

    @InjectMocks
    WorkServiceServiceImpl service;

    @Test
    public void getAllWorkServices_Should_Return_AllWorkServices() {
        List<WorkService> result = service.getAllWorkServices();
        result.add(createMockWorkService());

        // Assert
        Mockito.verify(mockWorkServiceRepository, Mockito.timeout(1)).getAllWorkServices();
    }

    @Test
    public void getById_Should_ReturnWorkService() {
        // Arrange
        Mockito.when(mockWorkServiceRepository.getById(1)).
                thenReturn(createMockWorkService());

        //Act
        WorkService result = service.getById(1);

        // Assert
        Assertions.assertEquals(1, result.getWorkServiceId());
        Assertions.assertEquals("Mock Service", result.getWorkServiceName());
        Assertions.assertEquals(150, result.getWorkServicePrice());
    }

    @Test
    public void getByName_Should_ReturnThatWorkService() {
        // Arrange
        Mockito.when(mockWorkServiceRepository.getByName("Z1")).
                thenReturn(createMockWorkService());

        //Act
        WorkService result = service.getByName("Z1");

        // Assert
        Assertions.assertEquals(1, result.getWorkServiceId());
        Assertions.assertEquals("Mock Service", result.getWorkServiceName());
        Assertions.assertEquals(150, result.getWorkServicePrice());
    }

    @Test
    public void create_Should_Throw_When_WorkServiceExists() {
        // Arrange
        var mockWorkService = createMockWorkService();
        var mockUser = createMockUser();

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockWorkService, mockUser));
    }

    @Test
    public void create_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockWorkService = createMockWorkService();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.create(mockWorkService, mockUser));
    }

    @Test
    public void create_Should_Pass_When_CreateWorkService() {
        // Arrange
        var mockWorkService = createMockWorkService();
        var mockUser = createMockUser();

        Mockito.when(mockWorkServiceRepository.getByName(mockWorkService.getWorkServiceName()))
                .thenThrow(new EntityNotFoundException("WorkService", "id", mockWorkService.getWorkServiceId()));

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.create(mockWorkService, mockUser));
    }

    @Test
    public void update_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockWorkService = createMockWorkService();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.update(mockWorkService, mockUser));
    }

    @Test
    public void update_Should_Throw_When_Put_Not_Existing_Id() {
        // Arrange
        var mockWorkService = createMockWorkService();
        var mockUser = createMockUser();

        Mockito.when(mockWorkServiceRepository.getById(mockWorkService.getWorkServiceId()))
                .thenThrow(new EntityNotFoundException("Work Service", "id", mockWorkService.getWorkServiceId()));

        // Act, Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.update(mockWorkService, mockUser));
    }

    @Test
    public void update_Should_Pass_When_UpdateWorkService() {
        // Arrange
        var mockWorkService = createMockWorkService();
        var mockUser = createMockUser();

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.update(mockWorkService, mockUser));
    }


    @Test
    public void delete_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockWorkService = createMockWorkService();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () ->
                service.delete(mockWorkService.getWorkServiceId(), mockUser));
    }

    @Test
    public void delete_Should_Pass_When_WorkService() {
        // Arrange
        var mockWorkService = createMockWorkService();
        var mockUser = createMockUser();


        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.delete(mockWorkService.getWorkServiceId(),mockUser));
    }
}
