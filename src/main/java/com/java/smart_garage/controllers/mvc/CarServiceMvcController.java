package com.java.smart_garage.controllers.mvc;


import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.contracts.serviceContracts.*;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.*;
import com.java.smart_garage.models.dto.CarServiceDto;
import com.java.smart_garage.models.viewDto.CarServiceViewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
    private final WorkServiceService workServiceService;
    private final AutomobileService carService;

    @Autowired
    public CarServiceMvcController(AuthenticationHelper authenticationHelper,
                                   CurrencyMultiplierService currencyMultiplierService,
                                   ModelConversionHelper modelConversionHelper,
                                   CarServiceService service, AutomobileService automobileService, UserService userService, WorkServiceService workServiceService, AutomobileService carService1, AutomobileService carService) {
        this.authenticationHelper = authenticationHelper;
        this.currencyMultiplierService = currencyMultiplierService;
        this.modelConversionHelper = modelConversionHelper;
        this.service = service;
        this.automobileService = automobileService;
        this.userService = userService;
        this.workServiceService = workServiceService;
        this.carService = carService;

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
    @GetMapping("/{id}")
    public String showAllCars(@PathVariable int id,@RequestParam Optional<Date> startingDate, @RequestParam Optional<Date> endingDate, @RequestParam Optional<String> currency,
                              Model model, HttpSession session) {
        User currentUser ;

            currentUser = authenticationHelper.tryGetUser(session);
            model.addAttribute("currentUser", currentUser);

        List<Automobile> cars = automobileService.getAllCarsByOwner(id);
        List<CarServiceViewDto> carsView = getCarServiceViewDto(startingDate,  endingDate, currency,  cars);
        model.addAttribute("carsView",carsView);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("currencies", currencyMultiplierService.getAllCurrency());
        return "carServices";
    }
    @GetMapping("/{id}/create")
    public String showCarsCreate(Model model, HttpSession session, @PathVariable int id) {
        User currentUser;

        try {
            currentUser = authenticationHelper.tryGetUser(session);
        } catch (UnauthorizedOperationException e) {
            return "authentication-fail";
        }

        if (!currentUser.isEmployee()) {
            return "authentication-fail";
        }

        model.addAttribute("carService", new CarServiceDto());
        model.addAttribute("cars",carService.getById(id));
        model.addAttribute("workService", workServiceService.getAllWorkServices(Optional.empty()));
        model.addAttribute("currentUser", currentUser);
        return "carService-create";
    }

    @PostMapping("/{id}/create")
    public String createCar(@PathVariable int id,@ModelAttribute("currentUser") User currentUser, Model model, HttpSession session, BindingResult bindingResult, @Valid @ModelAttribute CarServiceDto carServiceDto) {
        if (bindingResult.hasErrors()) {
            return "workService-create";
        }
        currentUser = authenticationHelper.tryGetUser(session);
        CarService carServiceWork = modelConversionHelper.carServiceFromDto(carServiceDto);
        Invoice invoice = modelConversionHelper.invoiceFromDto(carServiceDto.getInvoice());

        try {
            model.addAttribute("carService", carServiceDto);
            model.addAttribute("cars",carService.getById(id));
            model.addAttribute("workService", workServiceService.getAllWorkServices(Optional.empty()));
            model.addAttribute("currentUser", currentUser);
            service.create(carServiceWork,invoice, currentUser);
        } catch (DuplicateEntityException e) {
            bindingResult.rejectValue("carService", "text", e.getMessage());
            return "carService-create";
        }
        return "redirect:/carServices/" + currentUser.getUserId();
    }
    @GetMapping("/{id}/delete")
    public String showDeleteWarehousePage(@PathVariable int id,@ModelAttribute("currentUser") User currentUser, Model model, HttpSession session) {

        try {
            currentUser = authenticationHelper.tryGetUser(session);
            model.addAttribute("currentUser", currentUser);
        } catch (UnauthorizedOperationException e) {
            return "authentication-fail";
        }
        CarService carServiceToDelete = new CarService();

        List<CarService> carServices = service.getAllCarServicesByCar(id);

        model.addAttribute("carServices", carServices);
        model.addAttribute("carServiceToDelete", carServiceToDelete);
        return "carService-delete";
    }

    @PostMapping("/{id}/delete")
    public String removeShipment(@PathVariable int id,@ModelAttribute("currentUser") User currentUser,
                                 @ModelAttribute CarService carServiceToDelete,Model model,
                                 HttpSession session,
                                 BindingResult bindingResult) {

        try {
            currentUser = authenticationHelper.tryGetUser(session);
            model.addAttribute("currentUser", currentUser);
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("currentUser", currentUser);
        }

        if (!currentUser.isEmployee()) {
            return "authentication-fail";
        }
        try {
            service.delete(carServiceToDelete.getCarServicesId(), currentUser);
        } catch (RuntimeException e) {
            bindingResult.rejectValue("id", "email_error",
                    "Please archive all parcel entities bound to this warehouse before deleting");
            return "carService-delete";
        }


        return "redirect:/carServices/" + currentUser.getUserId();
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
