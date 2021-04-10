package com.java.smart_garage.ModelMaper;

import com.java.smart_garage.configuration.Md5Hashing;
import com.java.smart_garage.contracts.repoContracts.*;
import com.java.smart_garage.models.*;
import com.java.smart_garage.models.dto.*;
import com.java.smart_garage.models.viewDto.CarServiceViewDto;
import com.java.smart_garage.models.viewDto.WorkServiceView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ModelConversionHelper {

    private final ManufacturerRepository manufacturerRepository;
    private final AutomobileRepository automobileRepository;
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
                                 AutomobileRepository automobileRepository,
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
        this.automobileRepository = automobileRepository;
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


    public Automobile carFromDto(AutomobileDto automobileDto) {
        Automobile automobile = new Automobile();
        dtoToCarObject(automobileDto, automobile);
        return automobile;
    }

    public Automobile carFromDto(AutomobileDto automobileDto, int id) {
        Automobile automobile = automobileRepository.getById(id);
        dtoToCarObject(automobileDto, automobile);
        return automobile;
    }

    public CarServiceViewDto objectToView(Automobile automobile, List<WorkServiceView> workServiceViews){
        CarServiceViewDto carServiceViewDto = new CarServiceViewDto();
        carServiceViewDto.setCarModel(automobile.getModel().getModelName());
        carServiceViewDto.setCarManufacturer(automobile.getModel().getManufacturer().getManufacturerName());
        carServiceViewDto.setCarRegPlate(automobile.getRegistrationPlate());
        carServiceViewDto.setCarIdNumber(automobile.getIdentifications());
        carServiceViewDto.setCarOwner(automobile.getOwner().getPersonalInfo().getLastName());
        carServiceViewDto.setCarOwnerEmail(automobile.getOwner().getPersonalInfo().getEmail());
        carServiceViewDto.setWorkServices(workServiceViews);
        carServiceViewDto.setTotalPrice(Math.round(carServiceRepository.getCarServicesPrice(automobile.getId())*workServiceViews.get(0).getMultiplier()));
        return carServiceViewDto;
    }


    public WorkServiceView  objectToViewWork(CarService workService, double nameCurrency){
        WorkServiceView workServiceView = new WorkServiceView();
        double currency = workService.getService().getWorkServicePrice()*nameCurrency;
        workServiceView.setMultiplier(nameCurrency);
        workServiceView.setServiceName(workService.getService().getWorkServiceName());
        workServiceView.setPrice(Math.round(currency));
        workServiceView.setDate(workService.getInvoice().getDate());
        return workServiceView;
    }
    public Credential credentialFromDto(CredentialDto credentialDto) {

        Credential users = new Credential();
        dtoToCredentialObject(credentialDto, users);
        return users;
    }

    public Credential credentialFromDto(CredentialDto credentialDto, int id) {
        Credential credential = credentialRepository.getById(id);
        dtoToCredentialObject(credentialDto, credential);
        return credential;
    }

    public Invoice invoiceFromDto(InvoiceDto invoiceDto){
        Invoice invoice = new Invoice();
        dtoToInvoiceObject(invoiceDto,invoice);
        return invoice;
    }

    public User userFromDto(UserDto userDto){
        User user = new User();
        dtoToCredentialObject(userDto, user);
        return user;
    }

    public User userFromDto(UserDto userDto, int id) {
        User user = new User();
        dtoToCredentialObject(userDto, user);
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

    private void dtoToCredentialObject(UserDto userDto, User user) {
        Credential credential = credentialRepository.getById(userDto.getUserId());
        PersonalInfo personalInfo = personalInfoRepository.getById(userDto.getPersonalInfoId());
        UserType userType = userTypeRepository.getById(userDto.getUserType());
        user.setCredential(credential);
        user.setPersonalInfo(personalInfo);
        user.setUserType(userType);
    }

    private void dtoToCredentialObject(CredentialDto credentialDto, Credential credential) {
        credential.setUsername(credentialDto.getUsername());
        credential.setPassword(Md5Hashing.md5(credentialDto.getPassword()));

    }

    private void dtoToCarObject(AutomobileDto automobileDto, Automobile automobile) {
        Model model = modelRepository.getById(automobileDto.getModelId());
        Colour colour = coloursRepository.getById(automobileDto.getColourId());
        Engine engine = engineRepository.getById(automobileDto.getEngineId());
        User user = userRepository.getById(automobileDto.getOwnerId());

        automobile.setModel(model);
        automobile.setRegistrationPlate(automobileDto.getPlate());
        automobile.setIdentifications(automobileDto.getIdentification());
        automobile.setYear(automobileDto.getYear());
        automobile.setColour(colour);
        automobile.setEngine(engine);
        automobile.setOwner(user);
    }


    private void dtoToModelObject(ModelDto modelDto, Model model) {
        Manufacturer manufacturer = manufacturerRepository.getById(modelDto.getManufacturer());
        model.setModelName(modelDto.getModelName());
        model.setManufacturer(manufacturer);
    }
    private void dtoToCarServiceObject(CarServiceDto carServiceDto, CarService carService) {
        Automobile automobile = automobileRepository.getById(carServiceDto.getCarId());
        WorkService workService = workServiceRepository.getById(carServiceDto.getServiceId());
        Invoice invoice = invoiceRepository.getById(carServiceDto.getInvoiceId());
        carService.setCar(automobile);
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

    private static Map<String, Double> currencyValues() throws Exception {
        URL url = new URL("http://data.fixer.io/api/latest?access_key=5880fa2d2647553a6f7f6f196e2f085b");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        String someString = content.toString().substring(81, content.toString().length() - 2);

        Map<String, Double> myMap = new HashMap<String, Double>();
        String s = someString;
        String[] pairs = s.split(",");
        for (int i = 0; i < pairs.length; i++) {
            String pair = pairs[i];
            String[] keyValue = pair.split(":");
            myMap.put(keyValue[0].substring(1, 4), Double.valueOf(keyValue[1]));
        }
        in.close();
        return myMap;
    }

    public double getCurrency(String value) throws Exception {
        Map<String, Double> myMap = currencyValues();
        double currency = 0;
        for (String s : myMap.keySet()) {
           if(value.equals(s)){
             currency = myMap.get(s);
           }
        }return currency;
    }
}
