package com.java.smart_garage;

import com.java.smart_garage.models.*;

import java.sql.Date;

public class Helpers {

    public static Automobile createMockAutomobile(){
        Automobile mockCar = new Automobile();
        mockCar.setId(1);
        mockCar.setModel(createMockModel());
        mockCar.setRegistrationPlate("CA2313HM");
        mockCar.setIdentifications("Id12345678901234567");
        mockCar.setYear(2020);
        mockCar.setColour(createMockColour());
        mockCar.setEngine(createMockEngine());
        mockCar.setOwner(createMockUser());
        return mockCar;
    }

    public static CarService createMockCarService(){
        CarService mockCarService = new CarService();
        mockCarService.setCarServicesId(1);
        mockCarService.setCar(createMockAutomobile());
        mockCarService.setService(createMockWorkService());
        mockCarService.setInvoice(createMockInvoice());
        return mockCarService;
    }

    public static Colour createMockColour(){
        Colour mockColour = new Colour();
        mockColour.setColourId(1);
        mockColour.setColour("Red");
        return mockColour;
    }

    public static Credential createMockCredential(){
        Credential mockCredential = new Credential();
        mockCredential.setCredentialId(1);
        mockCredential.setUsername("MockUser");
        mockCredential.setPassword("MockPassword");
        return mockCredential;
    }

    public static Engine createMockEngine(){
        Engine mockEngine = new Engine();
        mockEngine.setEngineId(1);
        mockEngine.setFuel(createMockFuel());
        mockEngine.setHpw(150);
        mockEngine.setCubicCapacity(2000);
        return mockEngine;
    }

    public static Fuel createMockFuel(){
        Fuel mockFuel = new Fuel();
        mockFuel.setFuelId(1);
        mockFuel.setFuelName("Gas");
        return mockFuel;
    }

    public static Invoice createMockInvoice(){
        Invoice mockInvoice = new Invoice();
        Date mockDate = new Date(2000,12,1);
        mockInvoice.setInvoiceId(1);
        mockInvoice.setDate(mockDate);
        return mockInvoice;
    }

    public static Manufacturer createMockManufacturer(){
        Manufacturer mockManufacturer = new Manufacturer();
        mockManufacturer.setManufacturerId(1);
        mockManufacturer.setManufacturerName("BMW");
        return mockManufacturer;
    }

    public static Model createMockModel(){
        Model mockModel = new Model();
        mockModel.setModelId(1);
        mockModel.setModelName("Z1");
        mockModel.setManufacturer(createMockManufacturer());
        return mockModel;
    }

    public static PersonalInfo createMockPersonalInfo(){
        PersonalInfo mockPersonalInfo = new PersonalInfo();
        mockPersonalInfo.setPersonalId(1);
        mockPersonalInfo.setEmail("MockEmail@gmail.com");
        mockPersonalInfo.setFirstName("Mock1");
        mockPersonalInfo.setLastName("Mock2");
        mockPersonalInfo.setPhoneNumber("0884323130");
        return mockPersonalInfo;
    }

    public static User createMockUser(){
        User mockUser = new User();
        mockUser.setUserId(1);
        mockUser.setCredential(createMockCredential());
        mockUser.setPersonalInfo(createMockPersonalInfo());
        mockUser.setUserType(createMockUserType());
        return mockUser;
    }

    public static UserType createMockUserType(){
        UserType mockUserType = new UserType();
        mockUserType.setTypeId(1);
        mockUserType.setType("Employee");
        return mockUserType;
    }

    public static WorkService createMockWorkService(){
        WorkService mockWorkService = new WorkService();
        mockWorkService.setWorkServiceId(1);
        mockWorkService.setWorkServiceName("Mock Service");
        mockWorkService.setWorkServicePrice(150);
        return mockWorkService;
    }
}
