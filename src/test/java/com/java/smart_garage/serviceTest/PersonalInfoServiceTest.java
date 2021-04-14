package com.java.smart_garage.serviceTest;

import com.java.smart_garage.contracts.repoContracts.PersonalInfoRepository;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.PersonalInfo;
import com.java.smart_garage.services.PersonalInfoServiceImpl;
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
public class PersonalInfoServiceTest {

    @Mock
    PersonalInfoRepository mockPersonalInfoRepository;


    @InjectMocks
    PersonalInfoServiceImpl service;

    @Test
    public void getAllColours_Should_AllColours() {
        List<PersonalInfo> result = service.getAllPersonalInformations();
        result.add(createMockPersonalInfo());

        // Assert
        Mockito.verify(mockPersonalInfoRepository, Mockito.timeout(1)).getAllPersonalInformations();
    }

    @Test
    public void getById_Should_Return_Correct_PersonalInfo() {
        // Arrange
        Mockito.when(mockPersonalInfoRepository.getById(1)).
                thenReturn(createMockPersonalInfo());

        //Act
        PersonalInfo result = service.getById(1);

        // Assert
        Assertions.assertEquals(1, result.getPersonalId());
        Assertions.assertEquals("Mock1", result.getFirstName());
        Assertions.assertEquals("Mock2", result.getLastName());
        Assertions.assertEquals("MockEmail@gmail.com", result.getEmail());
        Assertions.assertEquals("0884323130", result.getPhoneNumber());

    }

    @Test
    public void getByFirstName_Should_Return_Correct_PersonalInfo() {
        // Arrange
        Mockito.when(mockPersonalInfoRepository.getByFirstName("Mock1")).
                thenReturn(createMockPersonalInfo());

        //Act
        PersonalInfo result = service.getByFirstName("Mock1");

        // Assert
        Assertions.assertEquals(1, result.getPersonalId());
        Assertions.assertEquals("Mock1", result.getFirstName());

    }

    @Test
    public void getByFirstName_Should_Throw_When_First_Name_Does_Not_Exists() {
        // Arrange
        Mockito.when(mockPersonalInfoRepository.getByFirstName("Test5")).
                thenThrow(new EntityNotFoundException("Personal Information", "name", "Test5"));

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.getByFirstName("Test5"));
    }

    @Test
    public void getByLastName_Should_Return_Correct_PersonalInfo() {
        // Arrange
        Mockito.when(mockPersonalInfoRepository.getByLastName("Mock2")).
                thenReturn(createMockPersonalInfo());

        //Act
        PersonalInfo result = service.getByLastName("Mock2");

        // Assert
        Assertions.assertEquals(1, result.getPersonalId());
        Assertions.assertEquals("Mock2", result.getLastName());
    }

    @Test
    public void getByLastName_Should_Throw_When_Last_Name_Does_Not_Exists() {
        // Arrange
        Mockito.when(mockPersonalInfoRepository.getByLastName("Test5")).
                thenThrow(new EntityNotFoundException("Personal Information", "name", "Test5"));

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.getByLastName("Test5"));
    }

    @Test
    public void getByEmail_Should_Return_Correct_PersonalInfo() {
        // Arrange
        Mockito.when(mockPersonalInfoRepository.getByEmail("MockEmail@gmail.com")).
                thenReturn(createMockPersonalInfo());

        //Act
        PersonalInfo result = service.getByEmail("MockEmail@gmail.com");

        // Assert
        Assertions.assertEquals(1, result.getPersonalId());
        Assertions.assertEquals("MockEmail@gmail.com", result.getEmail());

    }

    @Test
    public void getByEmail_Should_Throw_When_Fist_Name_Does_Not_Exists() {
        // Arrange
        Mockito.when(mockPersonalInfoRepository.getByEmail("Test5@abv.bg")).
                thenThrow(new EntityNotFoundException("Personal Information", "email", "Test5@abv.bg"));

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.getByEmail("Test5@abv.bg"));
    }



    @Test
    public void create_Should_Throw_When_ColourExists() {
        // Arrange
        var mockPersonalInfo = createMockPersonalInfo();
        var mockUser = createMockUser();

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockPersonalInfo, mockUser));
    }

    @Test
    public void create_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockPersonalInfo = createMockPersonalInfo();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.create(mockPersonalInfo, mockUser));
    }

    @Test
    public void create_Should_Pass_When_Put_Correct_Info() {
        // Arrange
        var mockPersonalInfo = createMockPersonalInfo();
        var mockUser = createMockUser();

        Mockito.when(mockPersonalInfoRepository.getByEmail(mockPersonalInfo.getEmail()))
                .thenThrow(new EntityNotFoundException("PersonalInfo", "name", mockPersonalInfo.getEmail()));

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.create(mockPersonalInfo, mockUser));
    }
    @Test
    public void delete_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockPersonalInfo = createMockPersonalInfo();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.delete(mockPersonalInfo.getPersonalId(),mockUser));
    }

    @Test
    public void delete_Should_Pass_When_CarService() {
        // Arrange
        var mockPersonalInfo = createMockPersonalInfo();

        var mockUser = createMockUser();


        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.delete(mockPersonalInfo.getPersonalId(),mockUser));
    }
}


