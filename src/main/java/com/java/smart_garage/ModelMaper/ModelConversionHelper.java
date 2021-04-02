package com.java.smart_garage.ModelMaper;

import com.java.smart_garage.contracts.repoContracts.*;
import com.java.smart_garage.models.*;
import com.java.smart_garage.models.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelConversionHelper {

    private final ManufacturerRepository manufacturerRepository;
    private final CarRepository carRepository;
    private final CarServiceRepository carServiceRepository;
    private final ColoursRepository coloursRepository;
    private final CustomerRepository customerRepository;
    private final EngineRepository engineRepository;
    private final FuelRepository fuelRepository;
    private final ModelRepository modelRepository;
    private final WorkServiceRepository workServiceRepository;
    private final UserTypeRepository userTypeRepository;
    private final UserRepository userRepository;
    private final PersonalInfoRepository personalInfoRepository;

    @Autowired
    public ModelConversionHelper(ManufacturerRepository manufacturerRepository,
                                 CarRepository carRepository,
                                 CarServiceRepository carServiceRepository,
                                 ColoursRepository coloursRepository,
                                 CustomerRepository customerRepository,
                                 EngineRepository engineRepository,
                                 FuelRepository fuelRepository,
                                 ModelRepository modelRepository,
                                 WorkServiceRepository workServiceRepository,
                                 UserTypeRepository userTypeRepository,
                                 UserRepository userRepository,
                                 PersonalInfoRepository personalInfoRepository) {
        this.manufacturerRepository = manufacturerRepository;
        this.carRepository = carRepository;
        this.carServiceRepository = carServiceRepository;
        this.coloursRepository = coloursRepository;
        this.customerRepository = customerRepository;
        this.engineRepository = engineRepository;
        this.fuelRepository = fuelRepository;
        this.modelRepository = modelRepository;
        this.workServiceRepository = workServiceRepository;
        this.userTypeRepository = userTypeRepository;
        this.userRepository = userRepository;
        this.personalInfoRepository = personalInfoRepository;
    }


    public Manufacturer manufacturerFromDto(ManufacturerDto manufacturerDto) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerName(manufacturerDto.getManufacturerName());
        return manufacturer;
    }

    public Manufacturer manufacturerFromDto(ManufacturerDto manufacturerDto, int id) {
        Manufacturer manufacturer = manufacturerRepository.getById(id);
        manufacturer.setManufacturerName(manufacturerDto.getManufacturerName());
        return manufacturer;
    }

    public Fuel fuelFromDto(FuelDto fuelDto) {
        Fuel fuel = new Fuel();
        fuel.setFuelName(fuelDto.getFuelName());
        return fuel;
    }

    public Fuel fuelFromDto(FuelDto fuelDto, int id) {
        Fuel fuel = fuelRepository.getById(id);
        fuel.setFuelName(fuelDto.getFuelName());
        return fuel;
    }

    public UserType userTypeFromDto(UserTypeDto userTypeDto) {
        UserType userType = new UserType();
        userType.setType(userTypeDto.getType());
        return userType;
    }

    public UserType userTypeFromDto(UserTypeDto userTypeDto, int id) {
        UserType userType = userTypeRepository.getById(id);
        userType.setType(userTypeDto.getType());
        return userType;
    }

    public Colour colourFromDto(ColourDto colourDto) {
        Colour colour = new Colour();
        colour.setColour(colourDto.getName());
        return colour;
    }

    public Colour colourFromDto(ColourDto colourDto, int id) {
        Colour colour = coloursRepository.getById(id);
        colour.setColour(colourDto.getName());
        return colour;
    }


    public WorkService workServiceFromDto(WorkServiceDto workServiceDto) {
        WorkService service = new WorkService();
        service.setWorkServiceName(workServiceDto.getWorkServiceName());
        return service;
    }

    public WorkService workServiceFromDto(WorkServiceDto workServiceDto, int id) {
        WorkService service = workServiceRepository.getById(id);
        service.setWorkServiceName(workServiceDto.getWorkServiceName());
        return service;
    }

    public Engine engineFromDto(EngineDto engineDto) {
        Engine engine = new Engine();
        dtoToEngineObject(engineDto, engine);
        return engine;
    }

    public Engine engineFromDto(EngineDto engineDto, int id) {
        Engine engine = engineRepository.getById(id);
        dtoToEngineObject(engineDto, engine);
        return engine;
    }

    public Model modelFromDto(ModelDto modelDto) {
        Model model = new Model();
        dtoToModelObject(modelDto, model);
        return model;
    }

    public Model modelFromDto(ModelDto modelDto, int id) {
        Model model = modelRepository.getById(id);
        dtoToModelObject(modelDto, model);
        return model;
    }

    public Car carFromDto(CarDto carDto) {
        Car car = new Car();
        dtoToCarObject(carDto, car);
        return car;
    }

    public Car carFromDto(CarDto carDto, int id) {
        Car car = carRepository.getById(id);
        dtoToCarObject(carDto, car);
        return car;
    }
    public User userFromDto(UserDto userDto) {

        User users = new User();
        dtoToUserObject(userDto, users);
        return users;
    }

    public User userFromDto(UserDto userDto, int id) {
        User user = userRepository.getById(id);
        dtoToUserObject(userDto, user);
        return user;
    }

    public Invoice invoiceFromDto(InvoiceDto invoiceDto){
        Invoice invoice = new Invoice();
        dtoToInvoiceObject(invoiceDto,invoice);
        return invoice;
    }

    public Customer customerFromDto(CustomerDto customerDto){
        Customer customer = new Customer();
        dtoToCustomerObject(customerDto,customer);
        return customer;
    }

    public Customer customerFromDto(CustomerDto customerDto, int id) {
        Customer customer = new Customer();
        dtoToCustomerObject(customerDto, customer);
        return customer;
    }

    public PersonalInfo personalInfoFromDto(PersonalInfoDto personalInfoDto) {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setFirstName(personalInfo.getFirstName());
        personalInfo.setLastName(personalInfo.getLastName());
        personalInfo.setEmail(personalInfo.getEmail());
        personalInfo.setPhoneNumber(personalInfo.getPhoneNumber());
        return personalInfo;
    }

    public PersonalInfo personalInfoFromDto(PersonalInfoDto personalInfoDto, int id) {
        PersonalInfo personalInfo = personalInfoRepository.getById(id);
        personalInfo.setFirstName(personalInfo.getFirstName());
        personalInfo.setLastName(personalInfo.getLastName());
        personalInfo.setEmail(personalInfo.getEmail());
        personalInfo.setPhoneNumber(personalInfo.getPhoneNumber());
        return personalInfo;
    }

    private void dtoToCustomerObject(CustomerDto customerDto, Customer customer) {
        User user = userRepository.getById(customerDto.getUserId());
        PersonalInfo personalInfo = personalInfoRepository.getById(customerDto.getPersonalInfoId());
        customer.setUser(user);
        customer.setPersonalInfo(personalInfo);
    }

    private void dtoToUserObject(UserDto userDto, User user) {
        UserType userType = userTypeRepository.getById(userDto.getUserType().getTypeId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setUserType(userType);
    }

    private void dtoToCarObject(CarDto carDto, Car car) {
        Model model = modelRepository.getById(carDto.getModelId());
        Colour colour = coloursRepository.getById(carDto.getColourId());
        Engine engine = engineRepository.getById(carDto.getEngineId());

        car.setModel(model);
        car.setRegistrationPlate(carDto.getPlate());
        car.setIdentifications(carDto.getIdentification());
        car.setYear(carDto.getYear());
        car.setColour(colour);
        car.setEngine(engine);
    }

    private void dtoToModelObject(ModelDto modelDto, Model model) {
        Manufacturer manufacturer = manufacturerFromDto(modelDto.getManufacturer());
        model.setModelName(modelDto.getModelName());
        model.setManufacturer(manufacturer);
    }

    private void dtoToEngineObject(EngineDto engineDto, Engine engine) {

        Fuel fuel = fuelRepository.getById(engineDto.getFuel());
        engine.setHpw(engineDto.getHorsePower());
        engine.setFuel(fuel);
        engine.setCubicCapacity(engineDto.getCc());
    }

    private void dtoToInvoiceObject(InvoiceDto invoiceDto, Invoice invoice) {
        invoice.setDate(invoiceDto.getDate());
    }

}
