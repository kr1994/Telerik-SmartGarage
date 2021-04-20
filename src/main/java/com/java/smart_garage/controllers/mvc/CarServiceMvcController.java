package com.java.smart_garage.controllers.mvc;


import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.contracts.serviceContracts.AutomobileService;
import com.java.smart_garage.contracts.serviceContracts.CarServiceService;
import com.java.smart_garage.contracts.serviceContracts.CurrencyMultiplierService;
import com.java.smart_garage.contracts.serviceContracts.UserService;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.*;
import com.java.smart_garage.models.viewDto.CarServiceViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/carServices")
public class CarServiceMvcController {
    private final AuthenticationHelper authenticationHelper;
    private final CurrencyMultiplierService currencyMultiplierService;
    private final ModelConversionHelper modelConversionHelper;
    private final CarServiceService service;
    private final AutomobileService automobileService;
    private final UserService userService;

    @Autowired
    public CarServiceMvcController(AuthenticationHelper authenticationHelper,
                                   CurrencyMultiplierService currencyMultiplierService,
                                   ModelConversionHelper modelConversionHelper,
                                   CarServiceService service, AutomobileService automobileService, UserService userService) {
        this.authenticationHelper = authenticationHelper;
        this.currencyMultiplierService = currencyMultiplierService;
        this.modelConversionHelper = modelConversionHelper;
        this.service = service;
        this.automobileService = automobileService;
        this.userService = userService;
    }

    @ModelAttribute
    public void userToCheck(Model model) {
        User currentUser = new User();
        UserType userType = new UserType();
        userType.setType("Anonymous");
        currentUser.setUserType(userType);
        model.addAttribute("currentUser", currentUser);
    }
    @GetMapping
    public String showAllServices(@ModelAttribute("currentUser") User currentUser,@RequestParam Optional<Date> startingDate, @RequestParam Optional<Date> endingDate, @RequestParam Optional<String> currency,
                                  Model model, HttpSession session){
        try {
            currentUser = authenticationHelper.tryGetUser(session);
            model.addAttribute("currentUser", currentUser);
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("currentUser", currentUser);
        }
        List<Automobile> cars = automobileService.getAllCars();
        List<CarServiceViewDto> carsView = getCarServiceViewDto(startingDate,  endingDate, currency,  cars);
        model.addAttribute("carsView",carsView);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("currencies", currencyMultiplierService.getAllCurrency());
        return "carServices";
    }
    private List<CarServiceViewDto> getCarServiceViewDto(@RequestParam Optional<Date> startingDate, @RequestParam Optional<Date> endingDate, @RequestParam Optional<String> currency, List<Automobile> cars) {
        List<CarServiceViewDto> carServiceViewDto = new ArrayList<>();

        for (Automobile car : cars) {
            if(!service.getAllCarServicesByView(startingDate,endingDate,car.getId(),currency).isEmpty())
                carServiceViewDto.add(modelConversionHelper.objectToView(car,service.getAllCarServicesByView(startingDate,endingDate,car.getId(),currency)));
        }
        return carServiceViewDto;
    }
}
