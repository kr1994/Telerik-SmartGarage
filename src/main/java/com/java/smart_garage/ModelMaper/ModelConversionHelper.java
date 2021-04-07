package com.java.smart_garage.ModelMaper;

import com.java.smart_garage.configuration.Md5Hashing;
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
    private final UserRepository userRepository;
    private final EngineRepository engineRepository;
    private final FuelRepository fuelRepository;
    private final ModelRepository modelRepository;
    private final WorkServiceRepository workServiceRepository;
    private final UserTypeRepository userTypeRepository;
    private final CredentialRepository credentialRepository;
    private final PersonalInfoRepository personalInfoRepository;
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public ModelConversionHelper(ManufacturerRepository manufacturerRepository,
                                 CarRepository carRepository,
                                 CarServiceRepository carServiceRepository,
                                 ColoursRepository coloursRepository,
                                 UserRepository userRepository,
                                 EngineRepository engineRepository,
                                 FuelRepository fuelRepository,
                                 ModelRepository modelRepository,
                                 WorkServiceRepository workServiceRepository,
                                 UserTypeRepository userTypeRepository,
                                 CredentialRepository credentialRepository,
                                 PersonalInfoRepository personalInfoRepository,
                                 InvoiceRepository invoiceRepository) {
        this.manufacturerRepository = manufacturerRepository;
        this.carRepository = carRepository;
        this.carServiceRepository = carServiceRepository;
        this.coloursRepository = coloursRepository;
        this.userRepository = userRepository;
        this.engineRepository = engineRepository;
        this.fuelRepository = fuelRepository;
        this.modelRepository = modelRepository;
        this.workServiceRepository = workServiceRepository;
        this.userTypeRepository = userTypeRepository;
        this.credentialRepository = credentialRepository;
        this.personalInfoRepository = personalInfoRepository;
        this.invoiceRepository = invoiceRepository;
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
        service.setWorkServicePrice(workServiceDto.getWorkServicePrice());
        return service;
    }

    public WorkService workServiceFromDto(WorkServiceDto workServiceDto, int id) {
        WorkService service = workServiceRepository.getById(id);
        service.setWorkServiceName(workServiceDto.getWorkServiceName());
        service.setWorkServicePrice(workServiceDto.getWorkServicePrice());
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

    public CarService carServiceFromDto(CarServiceDto carServiceDto) {
        CarService carService = new CarService();
        dtoToCarServiceObject(carServiceDto, carService);
        return carService;
    }


    public CarService carServiceFromDto(CarServiceDto carServiceDto, int id) {
        CarService carService = carServiceRepository.getById(id);
        dtoToCarServiceObject(carServiceDto, carService);
        return carService;
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
    public Credential userFromDto(CredentialDto credentialDto) {

        Credential users = new Credential();
        dtoToUserObject(credentialDto, users);
        return users;
    }

    public Credential userFromDto(CredentialDto credentialDto, int id) {
        Credential credential = credentialRepository.getById(id);
        dtoToUserObject(credentialDto, credential);
        return credential;
    }

    public Invoice invoiceFromDto(InvoiceDto invoiceDto){
        Invoice invoice = new Invoice();
        dtoToInvoiceObject(invoiceDto,invoice);
        return invoice;
    }

    public User customerFromDto(UserDto userDto){
        User user = new User();
        dtoToUserObject(userDto, user);
        return user;
    }

    public User customerFromDto(UserDto userDto, int id) {
        User user = new User();
        dtoToUserObject(userDto, user);
        return user;
    }

    public PersonalInfo personalInfoFromDto(PersonalInfoDto personalInfoDto) {
        PersonalInfo personalInfo = new PersonalInfo();
        dtoToPersonalInfoObject (personalInfoDto, personalInfo);
        return personalInfo;
    }

    public PersonalInfo personalInfoFromDto(PersonalInfoDto personalInfoDto, int id) {
        PersonalInfo personalInfo = personalInfoRepository.getById(id);
        dtoToPersonalInfoObject (personalInfoDto, personalInfo);
        return personalInfo;
    }

    private void dtoToUserObject(UserDto userDto, User user) {
        Credential credential = credentialRepository.getById(userDto.getUserId());
        PersonalInfo personalInfo = personalInfoRepository.getById(userDto.getPersonalInfoId());
        UserType userType = userTypeRepository.getById(userDto.getUserType());
        user.setCredential(credential);
        user.setPersonalInfo(personalInfo);
    }

    private void dtoToUserObject(CredentialDto credentialDto, Credential credential) {
        credential.setUsername(credentialDto.getUsername());
        credential.setPassword(Md5Hashing.md5(credentialDto.getPassword()));

    }

    private void dtoToCarObject(CarDto carDto, Car car) {
        Model model = modelRepository.getById(carDto.getModelId());
        Colour colour = coloursRepository.getById(carDto.getColourId());
        Engine engine = engineRepository.getById(carDto.getEngineId());
        User user = userRepository.getById(carDto.getOwnerId());

        car.setModel(model);
        car.setRegistrationPlate(carDto.getPlate());
        car.setIdentifications(carDto.getIdentification());
        car.setYear(carDto.getYear());
        car.setColour(colour);
        car.setEngine(engine);
        car.setOwner(user);
    }

    private void dtoToModelObject(ModelDto modelDto, Model model) {
        Manufacturer manufacturer = manufacturerRepository.getById(modelDto.getManufacturer());
        model.setModelName(modelDto.getModelName());
        model.setManufacturer(manufacturer);
    }
    private void dtoToCarServiceObject(CarServiceDto carServiceDto, CarService carService) {
        Car car = carRepository.getById(carServiceDto.getCarId());
        WorkService workService = workServiceRepository.getById(carServiceDto.getServiceId());
        Invoice invoice = invoiceRepository.getById(carServiceDto.getInvoiceId());
        carService.setCar(car);
        carService.setService(workService);
        carService.setInvoice(invoice);

    }

    private void dtoToEngineObject(EngineDto engineDto, Engine engine) {

        Fuel fuel = fuelRepository.getById(engineDto.getFuel());
        engine.setHpw(engineDto.getHorsePower());
        engine.setFuel(fuel);
        engine.setCubicCapacity(engineDto.getCc());
    }

    private void dtoToPersonalInfoObject(PersonalInfoDto personalInfoDto, PersonalInfo personalInfo) {
        personalInfo.setFirstName(personalInfoDto.getFirstName());
        personalInfo.setLastName(personalInfoDto.getLastName());
        personalInfo.setEmail(personalInfoDto.getEmail());
        personalInfo.setPhoneNumber(personalInfoDto.getPhoneNumber());
    }

    private void dtoToInvoiceObject(InvoiceDto invoiceDto, Invoice invoice) {
        invoice.setDate(invoiceDto.getDate());
    }

}
