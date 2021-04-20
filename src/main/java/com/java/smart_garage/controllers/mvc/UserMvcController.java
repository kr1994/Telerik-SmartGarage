package com.java.smart_garage.controllers.mvc;

import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.configuration.Md5Hashing;
import com.java.smart_garage.contracts.serviceContracts.UserService;
import com.java.smart_garage.contracts.serviceContracts.UserTypeService;
import com.java.smart_garage.exceptions.DuplicateEntityException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserMvcController {

    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final UserTypeService userTypeService;

    public UserMvcController(UserService userService,
                             AuthenticationHelper authenticationHelper,
                             UserTypeService userTypeService) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.userTypeService = userTypeService;
    }


    @GetMapping
    public String showAllUsers(@ModelAttribute("currentUser") User currentUser,
                                  Model model, HttpSession session){
        try {
            currentUser = authenticationHelper.tryGetUser(session);
            model.addAttribute("currentUser", currentUser);
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("currentUser", currentUser);
        }
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
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

        User user = userService.getById(id);
        PersonalInfo personalInfoDto = user.getPersonalInfo();
        UserType chosenType = new UserType();
        Credential credential = user.getCredential();

        model.addAttribute("usertype", chosenType);
        model.addAttribute("credential", credential);
        model.addAttribute("personal_info", personalInfoDto);
        model.addAttribute("currentUser", currentUser);
        return "user-update";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable int id,
                             @ModelAttribute("personal_info") PersonalInfo personalInfo,
                             @ModelAttribute("usertype") UserType userType,
                             @ModelAttribute("credential") Credential credential,
                             BindingResult bindingResult,
                             HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "user-update";
        }
        try {
            User currentUser = authenticationHelper.tryGetUser(session);
            User user = pucToUser(personalInfo, userType, credential);
            userService.update(user, currentUser);
        } catch (DuplicateEntityException e) {
            bindingResult.rejectValue("work service", "text", e.getMessage());
            return "user-update";
        }

        return "redirect:/users";
    }

    private User pucToUser(PersonalInfo p, UserType type, Credential credential) {
        User user = new User();
        user.setPersonalInfo(p);
        user.setUserType(type);
        String password = credential.getPassword();
        credential.setPassword(Md5Hashing.md5(password));
        user.setCredential(credential);
        return user;
    }



}
