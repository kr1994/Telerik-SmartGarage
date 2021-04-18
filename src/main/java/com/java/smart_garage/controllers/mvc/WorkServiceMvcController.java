package com.java.smart_garage.controllers.mvc;

import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.contracts.serviceContracts.CurrencyMultiplierService;
import com.java.smart_garage.contracts.serviceContracts.UserService;
import com.java.smart_garage.contracts.serviceContracts.UserTypeService;
import com.java.smart_garage.contracts.serviceContracts.WorkServiceService;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.UserType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/workServices")
public class WorkServiceMvcController {

    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final UserTypeService userTypeService;
    private final WorkServiceService workService;
    private final CurrencyMultiplierService currencyMultiplierService;

    public WorkServiceMvcController(UserService userService,
                                    AuthenticationHelper authenticationHelper,
                                    UserTypeService userTypeService,
                                    WorkServiceService workService, CurrencyMultiplierService currencyMultiplierService) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.userTypeService = userTypeService;
        this.workService = workService;
        this.currencyMultiplierService = currencyMultiplierService;
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
        model.addAttribute("currency",(currency));
        model.addAttribute("workServices", workService.getAllWorkServices(currency));
        model.addAttribute("currencies", currencyMultiplierService.getAllCurrency());
        return "workServices";
    }
    @PostMapping
    public String changeCurrency(@ModelAttribute("currentUser") User currentUser,@ModelAttribute @RequestParam  Optional<String> currency,
                                  Model model, HttpSession session){


        try {
            currentUser = authenticationHelper.tryGetUser(session);
            model.addAttribute("currentUser", currentUser);
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("currentUser", currentUser);
        }
        model.addAttribute("currency",(currency));
        model.addAttribute("workServices", workService.getAllWorkServices(currency));
        model.addAttribute("currencies", currencyMultiplierService.getAllCurrency());
        return "workServices";
    }
}
