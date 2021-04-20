package com.java.smart_garage.controllers.mvc;

import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.contracts.serviceContracts.CurrencyMultiplierService;
import com.java.smart_garage.contracts.serviceContracts.UserService;
import com.java.smart_garage.contracts.serviceContracts.UserTypeService;
import com.java.smart_garage.contracts.serviceContracts.WorkServiceService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.UserType;
import com.java.smart_garage.models.WorkService;
import com.java.smart_garage.models.dto.WorkServiceDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/workServices")
public class WorkServiceMvcController {

    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final UserTypeService userTypeService;
    private final WorkServiceService workService;
    private final CurrencyMultiplierService currencyMultiplierService;
    private final ModelConversionHelper modelConversionHelper;

    public WorkServiceMvcController(UserService userService,
                                    AuthenticationHelper authenticationHelper,
                                    UserTypeService userTypeService,
                                    WorkServiceService workService, CurrencyMultiplierService currencyMultiplierService, ModelConversionHelper modelConversionHelper) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.userTypeService = userTypeService;
        this.workService = workService;
        this.currencyMultiplierService = currencyMultiplierService;
        this.modelConversionHelper = modelConversionHelper;
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
    public String showAllServices(@ModelAttribute("currentUser") User currentUser,@RequestParam Optional<String> currency,
                                  Model model, HttpSession session){
        try {
            currentUser = authenticationHelper.tryGetUser(session);
            model.addAttribute("currentUser", currentUser);
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("currentUser", currentUser);
        }
        List<WorkService> workServices = workService.getAllWorkServices(currency);
        model.addAttribute("currency",(currency));
        model.addAttribute("workServices", workServices);
        model.addAttribute("currencies", currencyMultiplierService.getAllCurrency());
        return "workServices";
    }
    @GetMapping("/create")
    public String showCarsCreate(Model model, HttpSession session) {
        User currentUser;

        try {
            currentUser = authenticationHelper.tryGetUser(session);
        } catch (UnauthorizedOperationException e) {
            return "authentication-fail";
        }

        if (!currentUser.isEmployee()) {
            return "authentication-fail";
        }
        workServiceParams(currentUser, model);
        return "workService-create";
    }

    @PostMapping("/create")
    public String createCar(@ModelAttribute("currentUser") User currentUser, @Valid @ModelAttribute WorkServiceDto workServiceDto, Model model, HttpSession session, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "workService-create";
        }
        currentUser = authenticationHelper.tryGetUser(session);
        WorkService service = modelConversionHelper.workServiceFromDto(workServiceDto);

        try {
            workServiceParams(currentUser, model);
            workService.create(service, currentUser);
        } catch (DuplicateEntityException e) {
            bindingResult.rejectValue("work service", "text", e.getMessage());
            return "workService-create";
        }

        return "redirect:/workServices";
    }
    @GetMapping("/{id}/update")
    public String showUpdatePage(@PathVariable int id, Model model, HttpSession session) {
        User currentUser;

        try {
            currentUser = authenticationHelper.tryGetUser(session);
        } catch (UnauthorizedOperationException e) {
            return "authentication-fail";
        }

        if (!currentUser.isEmployee()) {
            return "authentication-fail";
        }

        WorkService service = workService.getById(Optional.empty(), id);
        WorkServiceDto workServiceDto = modelConversionHelper.workServiceToDto(service);
        model.addAttribute("workservice", workServiceDto);
        model.addAttribute("currentUser", currentUser);
        return "workService-update";
    }

    @PostMapping("/{id}/update")
    public String updateShipment(@PathVariable int id, @ModelAttribute("workservice") WorkServiceDto workServiceDto, HttpSession session) {
        User currentUser = authenticationHelper.tryGetUser(session);
        WorkService service = modelConversionHelper.workServiceFromDto(workServiceDto, id);
        workService.update(service, currentUser);
        return "redirect:/workServices";
    }

    private void workServiceParams(@ModelAttribute("currentUser") User currentUser, Model model) {
        model.addAttribute("workservice", new WorkServiceDto());
        model.addAttribute("currentUser", currentUser);
    }

}
