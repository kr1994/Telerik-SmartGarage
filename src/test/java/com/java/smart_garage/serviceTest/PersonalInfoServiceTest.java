package com.java.smart_garage.serviceTest;

import com.java.smart_garage.contracts.repoContracts.PersonalInfoRepository;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.IncorrectPhoneException;
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
import static com.java.smart_garage.Helpers.createMockUser;

@ExtendWith(MockitoExtension.class)
public class PersonalInfoServiceTest {

    @Mock
    PersonalInfoRepository mockPersonalInfoRepository;

    @Mock
    PersonalInfoServiceImpl mockService;

    @InjectMocks
    PersonalInfoServiceImpl service;

    @Test
    public void getAllPersonalInformations_Should_Return_PersonalInformation() {
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
                thenReturn(List.of(createMockPersonalInfo()));

        //Act
        List<PersonalInfo> result = service.getByFirstName("Mock1");

        // Assert
        Assertions.assertEquals(1, result.get(0).getPersonalId());
        Assertions.assertEquals("Mock1", result.get(0).getFirstName());

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
                thenReturn(List.of(createMockPersonalInfo()));

        //Act
        List<PersonalInfo> result = service.getByLastName("Mock2");

        // Assert
        Assertions.assertEquals(1, result.get(0).getPersonalId());
        Assertions.assertEquals("Mock2", result.get(0).getLastName());
    }

    @Test
    public void getByLastName_Should_Throw_When_Last_Name_Does_Not_Exists() {
        // Arrange
        Mockito.when(mockPersonalInfoRepository.getByLastName("Test5")).
                thenThrow(new EntityNotFoundException("Personal Information", "name", "Test5"));

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.getByLastName("Test5"));
    }

    @Test
    public void getByEmail_Should_Return_Correct_PersonalInformation() {
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
    public void create_Should_Throw_When_Personal_Information_Exists() {
        // Arrange
        var mockPersonalInfo = createMockPersonalInfo();
        var mockUser = createMockUser();

        // Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class, () -> service.create(mockPersonalInfo, mockUser));
    }

    @Test
    public void create_Should_Throw_IncorrectPhoneException_When_Phone_Number_Is_With_Wrong_Pattern() {
        // Arrange
        var mockPersonalInfo = createMockPersonalInfo();
        var mockUser = createMockUser();
        mockUser.getPersonalInfo().setPhoneNumber("333");

        Mockito.when(mockPersonalInfoRepository.getByEmail(mockPersonalInfo.getEmail()))
                .thenThrow(new EntityNotFoundException("PersonalInfo", "name", mockPersonalInfo.getEmail()));

        // Act, Assert
        Assertions.assertThrows(IncorrectPhoneException.class, () -> service.create(mockPersonalInfo, mockUser));
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
    public void create_Should_Pass_When_Put_Correct_Personal_Information() {
        // Arrange
        var mockPersonalInfo = createMockPersonalInfo();
        var mockUser = createMockUser();

        Mockito.when(mockPersonalInfoRepository.getByEmail(mockPersonalInfo.getEmail()))
                .thenThrow(new EntityNotFoundException("PersonalInfo", "name", mockPersonalInfo.getEmail()));

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.create(mockPersonalInfo, mockUser));
    }

    @Test
    public void update_Should_Pass_When_Put_Correct_Personal_Information() {
        // Arrange
        var mockPersonalInfo = createMockPersonalInfo();
        var mockUser = createMockUser();

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.update(mockPersonalInfo, mockUser));
    }

    @Test
    public void update_Should_Throw_When_Put_Not_Existing_Id() {
        // Arrange
        var mockPersonalInfo = createMockPersonalInfo();
        var mockUser = createMockUser();

        Mockito.when(mockPersonalInfoRepository.getById(mockPersonalInfo.getPersonalId()))
                .thenThrow(new EntityNotFoundException("PersonalInfo", "id", mockPersonalInfo.getPersonalId()));

        // Act, Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.update(mockPersonalInfo, mockUser));
    }

    @Test
    public void update_Should_Throw_When_UserIsCustomer() {
        // Arrange
        var mockPersonalInfo = createMockPersonalInfo();
        var mockUser = createMockUser();
        mockUser.setUserType(createMockUserTypeCustomer());

        // Act, Assert
        Assertions.assertThrows(UnauthorizedOperationException.class, () -> service.update(mockPersonalInfo, mockUser));
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
    public void delete_Should_Pass_When_PersonalInformation() {
        // Arrange
        var mockPersonalInfo = createMockPersonalInfo();

        var mockUser = createMockUser();

        // Act, Assert
        Assertions.assertDoesNotThrow(() -> service.delete(mockPersonalInfo.getPersonalId(),mockUser));
    }

    @Test
    public void truePhoneNumber_Should_Return_True_With_Correct_Number_Pattern() {
        var phoneNumber = "0887213799";
        Assertions.assertTrue(() -> service.truePhoneNumber(phoneNumber));
    }

    @Test
    public void truePhoneNumber_Should_Return_False_With_Wrong_Number_Pattern() {
        var phoneNumber = "1887213799";
        Assertions.assertFalse(() -> service.truePhoneNumber(phoneNumber));
    }
}


