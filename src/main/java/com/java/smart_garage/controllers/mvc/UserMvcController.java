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
        userDto.setUserId(user.getUserId());
        userDto.getCredential().setCredentialId(user.getCredential().getCredentialId());
        userDto.getPersonalInfo().setPersonalId(user.getPersonalInfo().getPersonalId());
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
            User user = pucToUser(userDto.getPersonalInfo(), userDto.getCredential(), userDto.getUserType());
            user.setUserId(id);
            userService.update(user, currentUser);
        } catch (DuplicateEntityException e) {
            bindingResult.rejectValue("user", "text", e.getMessage());
            return "user-update";
        }

        return "redirect:/users";
    }

    private User pucToUser(PersonalInfo p, Credential credential, UserType userType) {
        User user = new User();
        int personalId = personalInfoService.getByEmail(p.getEmail()).getPersonalId();
        user.setPersonalInfo(p);
        user.getPersonalInfo().setPersonalId(personalId);
        int credentialId = credentialService.getByUsername(credential.getUsername()).getCredentialId();
        String password = credential.getPassword();
        credential.setPassword(Md5Hashing.md5(password));
        user.setCredential(credential);
        user.getCredential().setCredentialId(credentialId);
        user.setUserType(userType);
        int typeId = userTypeService.getByName(userType.getType()).getTypeId();
        user.getUserType().setTypeId(typeId);
        return user;
    }



}
