package com.java.smart_garage.ModelMaper;

import com.java.smart_garage.contracts.repoContracts.*;
import com.java.smart_garage.models.*;
import com.java.smart_garage.models.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelConversionHelper {

    private final ManufacturerRepository manufacturerRepository;
    private final HorsePowerRepository horsePowerRepository;
    private final CarRepository carRepository;
    private final CarServiceRepository carServiceRepository;
    private final ColoursRepository coloursRepository;
    private final CubicCapacityRepository cubicCapacityRepository;
    private final CustomerRepository customerRepository;
    private final EngineRepository engineRepository;
    private final FuelRepository fuelRepository;
    private final IdentificationRepository identificationRepository;
    private final ModelRepository modelRepository;
    private final RegistrationPlateRepository registrationPlateRepository;
    private final ServiceRepository serviceRepository;
    private final UserTypeRepository userTypeRepository;
    private final UserRepository userRepository;
    private final YearRepository yearRepository;

    @Autowired
    public ModelConversionHelper(ManufacturerRepository manufacturerRepository,
                                 HorsePowerRepository horsePowerRepository,
                                 CarRepository carRepository,
                                 CarServiceRepository carServiceRepository,
                                 ColoursRepository coloursRepository,
                                 CubicCapacityRepository cubicCapacityRepository,
                                 CustomerRepository customerRepository,
                                 EngineRepository engineRepository,
                                 FuelRepository fuelRepository,
                                 IdentificationRepository identificationRepository,
                                 ModelRepository modelRepository,
                                 RegistrationPlateRepository registrationPlateRepository,
                                 ServiceRepository serviceRepository,
                                 UserTypeRepository userTypeRepository,
                                 UserRepository userRepository,
                                 YearRepository yearRepository) {
        this.manufacturerRepository = manufacturerRepository;
        this.horsePowerRepository = horsePowerRepository;
        this.carRepository = carRepository;
        this.carServiceRepository = carServiceRepository;
        this.coloursRepository = coloursRepository;
        this.cubicCapacityRepository = cubicCapacityRepository;
        this.customerRepository = customerRepository;
        this.engineRepository = engineRepository;
        this.fuelRepository = fuelRepository;
        this.identificationRepository = identificationRepository;
        this.modelRepository = modelRepository;
        this.registrationPlateRepository = registrationPlateRepository;
        this.serviceRepository = serviceRepository;
        this.userTypeRepository = userTypeRepository;
        this.userRepository = userRepository;
        this.yearRepository = yearRepository;
    }


    public Manufacturer manufacturerFromDto(ManufacturerDto manufacturerDto) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerName(manufacturerDto.getName());
        return manufacturer;
    }

    public Manufacturer manufacturerFromDto(ManufacturerDto manufacturerDto, int id) {
        Manufacturer manufacturer = manufacturerRepository.getById(id);
        manufacturer.setManufacturerName(manufacturerDto.getName());
        return manufacturer;
    }

    public HorsePower horsePowerFromDto(HorsePowerDto horsePowerDto) {
        HorsePower horsePower = new HorsePower();
        horsePower.setPower(horsePowerDto.getPower());
        return horsePower;
    }

    public HorsePower horsePowerFromDto(HorsePowerDto horsePowerDto, int id) {
        HorsePower horsePower = horsePowerRepository.getById(id);
        horsePower.setPower(horsePowerDto.getPower());
        return horsePower;
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

    public CubicCapacity cubicCapacityFromDto(CubicCapacityDto cubicCapacityDto) {
        CubicCapacity cubicCapacity = new CubicCapacity();
        cubicCapacity.setCubicCapacity(cubicCapacityDto.getCubicCapacity());
        return cubicCapacity;
    }

    public CubicCapacity cubicCapacityFromDto(CubicCapacityDto cubicCapacityDto, int id) {
        CubicCapacity cubicCapacity = cubicCapacityRepository.getById(id);
        cubicCapacity.setCubicCapacity(cubicCapacityDto.getCubicCapacity());
        return cubicCapacity;
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

    public Year yearFromDto(YearDto yearDto) {
        Year year = new Year();
        year.setYear(yearDto.getYear());
        return year;
    }

    public Year yearFromDto(YearDto yearDto, int id) {
        Year year = yearRepository.getById(id);
        year.setYear(yearDto.getYear());
        return year;
    }

    public Identification identificationFromDto(IdentificationDto identificationDto) {
        Identification identification = new Identification();
        identification.setIdentification(identificationDto.getIdentification());
        return identification;
    }

    public Identification identificationFromDto(IdentificationDto identificationDto, int id) {
        Identification identification = identificationRepository.getById(id);
        identification.setIdentification(identificationDto.getIdentification());
        return identification;
    }

    public RegistrationPlate registrationPlateFromDto(PlateDto plateDto) {
        RegistrationPlate registrationPlate = new RegistrationPlate();
        registrationPlate.setPlateNumber(plateDto.getPlate());
        return registrationPlate;
    }

    public RegistrationPlate registrationPlateFromDto(PlateDto plateDto, int id) {
        RegistrationPlate registrationPlate = registrationPlateRepository.getById(id);
        registrationPlate.setPlateNumber(plateDto.getPlate());
        return registrationPlate;
    }

    public WorkService serviceFromDto(ServiceDto serviceDto) {
        WorkService service = new WorkService();
        service.setServiceName(serviceDto.getServiceName());
        return service;
    }

    public WorkService serviceFromDto(ServiceDto serviceDto, int id) {
        WorkService service = serviceRepository.getById(id);
        service.setServiceName(serviceDto.getServiceName());
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
    public User usersFromDto(UserDto userDto) {

        User users = new User();
        dtoToUserObject(userDto, users);
        return users;
    }

    public User usersFromDto(UserDto userDto, int id) {
        User user = userRepository.getById(id);
        dtoToUserObject(userDto, user);
        return user;
    }

    public Customer customerFromDto(CustomerDto customerDto){
        Customer customer = new Customer();
        dtoToCustomerObject(customerDto,customer);
        return customer;
    }
    public Customer customerFromDto(CustomerDto customerDto,int id){
        Customer customer = new Customer();
        dtoToCustomerObject(customerDto,customer);
        return customer;
    }


    private void dtoToCustomerObject(CustomerDto customerDto, Customer customer) {
        User user = usersFromDto(customerDto.getUserDto());
        customer.setUser(user);
        customer.setPhoneNumber(customerDto.getPhoneNumber());
    }

    private void dtoToUserObject(UserDto userDto, User user) {
        UserType userType = userTypeRepository.getById(userDto.getUserType().getTypeId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUserType(userType);
    }

    private void dtoToCarObject(CarDto carDto, Car car) {
        Model model = modelFromDto(carDto.getModel());
        RegistrationPlate plate = registrationPlateFromDto(carDto.getPlate());
        Identification identification = identificationFromDto(carDto.getIdentification());
        Year year = yearFromDto(carDto.getYear());
        Colour colour = colourFromDto(carDto.getColour());
        Engine engine = engineFromDto(carDto.getEngine());

        car.setModel(model);
        car.setRegistrationPlate(plate);
        car.setIdentifications(identification);
        car.setYear(year);
        car.setColour(colour);
        car.setEngine(engine);
    }

    private void dtoToModelObject(ModelDto modelDto, Model model) {
        Manufacturer manufacturer = manufacturerFromDto(modelDto.getManufacturer());
        model.setModelName(modelDto.getModelName());
        model.setManufacturer(manufacturer);
    }

    private void dtoToEngineObject(EngineDto engineDto, Engine engine) {
        HorsePower horsePower = horsePowerFromDto(engineDto.getHorsePower());
        Fuel fuel = fuelFromDto(engineDto.getFuel());
        CubicCapacity cubicCapacity = cubicCapacityFromDto(engineDto.getCc());
        engine.setHpw(horsePower);
        engine.setFuel(fuel);
        engine.setCubicCapacity(cubicCapacity);
    }

}
