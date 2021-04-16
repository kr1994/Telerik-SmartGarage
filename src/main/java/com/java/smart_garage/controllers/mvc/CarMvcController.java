package com.java.smart_garage.controllers.mvc;

import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.contracts.serviceContracts.*;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.models.Automobile;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.UserType;
import com.java.smart_garage.models.dto.AutomobileDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/cars")
public class CarMvcController {

    private final AutomobileService automobileService;
    private final AuthenticationHelper authenticationHelper;
    private final UserService userService;
    private final EngineService engineService;
    private final ColourService colourService;
    private final ModelService modelService;
    private final ModelConversionHelper modelConversionHelper;
    private final ManufactureService manufactureService;


    public CarMvcController(AutomobileService automobileService, AuthenticationHelper authenticationHelper, UserService userService, EngineService engineService, ColourService colourService, ModelService modelService, ModelConversionHelper modelConversionHelper, ManufactureService manufactureService) {
        this.automobileService = automobileService;
        this.authenticationHelper = authenticationHelper;
        this.userService = userService;
        this.engineService = engineService;
        this.colourService = colourService;
        this.modelService = modelService;
        this.modelConversionHelper = modelConversionHelper;
        this.manufactureService = manufactureService;
    }

    @ModelAttribute
    public void userToCheck(Model model) {
        User currentUser = new User();
        UserType userType = new UserType();
        userType.setType("Employee");
        currentUser.setUserType(userType);
        model.addAttribute("currentUser", currentUser);
    }

    @GetMapping
    public String showAllCars(Model model, HttpSession session) {
        User currentUser = new User();
        UserType userType = new UserType();
        userType.setType("Employee");
        currentUser.setUserType(userType);
//
//        try {
//            currentUser = authenticationHelper.tryGetUser(session);
//        } catch (UnauthorizedOperationException e) {
//            return "authentication-fail";
//        }
//
//        if (!currentUser.isEmployee()) {
//            return "authentication-fail";
//        }
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("cars", automobileService.getAllCars());
        model.addAttribute("currentUser", currentUser);
        return "cars";
    }
    @GetMapping("/create")
    public String showCarsCreate(Model model, HttpSession session) {
        User currentUser = new User();
        UserType userType = new UserType();
        userType.setType("Employee");
        currentUser.setUserType(userType);
//
//        try {
//            currentUser = authenticationHelper.tryGetUser(session);
//        } catch (UnauthorizedOperationException e) {
//            return "authentication-fail";
//        }
//
//        if (!currentUser.isEmployee()) {
//            return "authentication-fail";
//        }
        carPartsList(currentUser, model);
        return "car-create";
    }

    @PostMapping("/create")
    public String createCar(@ModelAttribute("currentUser") User currentUser, @Valid @ModelAttribute AutomobileDto automobileDto, Model model, HttpSession session, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "car-create";
        }
        currentUser = new User();
        UserType userType = new UserType();
        userType.setType("Employee");
        currentUser.setUserType(userType);
        //currentUser = authenticationHelper.tryGetUser(session);
        Automobile automobile = modelConversionHelper.carFromDto(automobileDto);

        try {
            carPartsList(currentUser, model);
            automobileService.create(automobile, currentUser);
        } catch (DuplicateEntityException e) {
            bindingResult.rejectValue("car", "text", e.getMessage());
            return "car-create";
        }

        return "redirect:/cars";
    }

    private void carPartsList(@ModelAttribute("currentUser") User currentUser, Model model) {
        model.addAttribute("car", new AutomobileDto());
        model.addAttribute("manufacturer", manufactureService.getAllManufacturers());
        model.addAttribute("models", modelService.getAllModels());
        model.addAttribute("colours", colourService.getAllColours());
        model.addAttribute("engines", engineService.getAllEngines());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("currentUser", currentUser);
    }

}
