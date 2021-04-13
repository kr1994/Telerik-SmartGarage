package com.java.smart_garage.serviceTest;

import com.java.smart_garage.contracts.repoContracts.ColoursRepository;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Colour;
import com.java.smart_garage.services.ColourServiceImpl;
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
public class ColourServiceITest {

        @Mock
        ColoursRepository mockColoursRepository;


        @InjectMocks
        ColourServiceImpl service;

    @Test
    public void getAllColours_Should_AllColours() {
        List<Colour> result = service.getAllColours();
        result.add(createMockColour());

        // Assert
        Mockito.verify(mockColoursRepository, Mockito.timeout(1)).getAllColours();
    }

    @Test
    public void getById_Should_ReturnColours() {
        // Arrange
        Mockito.when(mockColoursRepository.getById(1)).
                thenReturn(createMockColour());

        //Act
        Colour result = service.getById(1);

        // Assert
        Assertions.assertEquals(1, result.getColourId());
        Assertions.assertEquals("Red", result.getColour());

    }

    @Test
    public void getByName_Should_ReturnColours() {
        // Arrange
        Mockito.when(mockColoursRepository.getByName("Red")).
                thenReturn(createMockColour());

        //Act
        Colour result = service.getByName("Red");

        // Assert
        Assertions.assertEquals(1, result.getColourId());
        Assertions.assertEquals("Red", result.getColour());

    }
    @Test
    public void create_Should_Throw_When_ColourExists() {
        // Arrange
        var mockColour = createMockColour();
        var mockUser = createMockUser();

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockColour, mockUser));
    }

    @Test
    public void create_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockColour = createMockColour();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.create(mockColour, mockUser));
    }

    @Test
    public void create_Should_Pass_When_CreatCarService() {
        // Arrange
        var mockColour = createMockColour();
        var mockUser = createMockUser();

        Mockito.when(mockColoursRepository.getByName(mockColour.getColour()))
                .thenThrow(new EntityNotFoundException("CarService", "id", mockColour.getColourId()));

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.create(mockColour, mockUser));
    }
    @Test
    public void delete_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockColour = createMockColour();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.delete(mockColour.getColourId(),mockUser));
    }

    @Test
    public void delete_Should_Pass_When_CarService() {
        // Arrange
        var mockColour = createMockColour();
        var mockUser = createMockUser();


        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.delete(mockColour.getColourId(),mockUser));
    }
}
