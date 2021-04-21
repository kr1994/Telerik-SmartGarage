package com.java.smart_garage.controllers.mvc;

import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.configuration.Md5Hashing;
import com.java.smart_garage.contracts.serviceContracts.CredentialService;
import com.java.smart_garage.contracts.serviceContracts.PersonalInfoService;
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
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserMvcController {

    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final UserTypeService userTypeService;
    private final CredentialService credentialService;
    private final PersonalInfoService personalInfoService;

    public UserMvcController(UserService userService,
                             AuthenticationHelper authenticationHelper,
                             UserTypeService userTypeService,
                             CredentialService credentialService,
                             PersonalInfoService personalInfoService) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.userTypeService = userTypeService;
        this.credentialService = credentialService;
        this.personalInfoService = personalInfoService;
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
        User userDto = user;
        model.addAttribute("user", userDto);
        model.addAttribute("currentUser", currentUser);
        return "user-update";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable int id,
                             @ModelAttribute("user") User userDto,
                             Model model,
                             BindingResult bindingResult,
                             HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "user-update";
        }
        try {
            User currentUser = authenticationHelper.tryGetUser(session);
            model.addAttribute("currentUser", currentUser);
            User updatedUser = userService.getById(id);
            userDtoToUser(updatedUser, userDto);
            userService.update(updatedUser, currentUser);
        } catch (DuplicateEntityException e) {
            bindingResult.rejectValue("user", "text", e.getMessage());
            return "user-update";
        }

        return "redirect:/users";
    }

    private void userDtoToUser(User user, User userDto) {
        user.getPersonalInfo().setFirstName(userDto.getPersonalInfo().getFirstName());
        user.getPersonalInfo().setLastName(userDto.getPersonalInfo().getLastName());
        user.getPersonalInfo().setEmail(userDto.getPersonalInfo().getEmail());
        user.getPersonalInfo().setPhoneNumber(userDto.getPersonalInfo().getPhoneNumber());
        String password = userDto.getCredential().getPassword();
        userDto.getCredential().setPassword(Md5Hashing.md5(password));
        user.getCredential().setPassword(userDto.getCredential().getPassword());
        user.getUserType().setType(userDto.getUserType().getType());
    }
}
