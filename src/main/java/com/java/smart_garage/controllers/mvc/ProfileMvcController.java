package com.java.smart_garage.controllers.mvc;

import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.configuration.Md5Hashing;
import com.java.smart_garage.contracts.serviceContracts.CredentialService;
import com.java.smart_garage.contracts.serviceContracts.PersonalInfoService;
import com.java.smart_garage.contracts.serviceContracts.UserService;
import com.java.smart_garage.contracts.serviceContracts.UserTypeService;
import com.java.smart_garage.exceptions.AuthenticationHelperException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.dto.PersonalInfoDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileMvcController {


    private final UserService userService;
    private final AuthenticationHelper authenticationHelper;
    private final CredentialService credentialService;
    private final PersonalInfoService personalInfoService;

    public ProfileMvcController(UserService userService,
                                AuthenticationHelper authenticationHelper,
                                CredentialService credentialService,
                                PersonalInfoService personalInfoService) {
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
        this.credentialService = credentialService;
        this.personalInfoService = personalInfoService;
    }

    @GetMapping
    public String showUserInformation(Model model,
                                      HttpSession session) {
        try {
            User currentUser = authenticationHelper.tryGetUser(session);
            model.addAttribute("currentUser", currentUser);
        } catch (UnauthorizedOperationException e) {
            throw new UnauthorizedOperationException("No logged in user.");
        }

        return "profile";
    }

    @GetMapping("/reset-password")
    public String resetPassword(Model model,
                                HttpSession session,
                                @ModelAttribute String oldTypedPassword,
                                @ModelAttribute String newPassword) {

        User currentUser = authenticationHelper.tryGetUser(session);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("oldPassword", oldTypedPassword);
        model.addAttribute("newPassword", newPassword);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String changePassword(Model model,
                                 @ModelAttribute("newPassword") String newPassword,
                                 HttpSession session) {


        try {
            User currentUser = authenticationHelper.tryGetUser(session);
            model.addAttribute("currentUser", currentUser);
            String oldTypedPassword = (String) model.getAttribute("oldPassword");
            String oldPassword = userService.getById(currentUser.getUserId()).getCredential().getPassword();
            model.addAttribute("newPassword", newPassword);
            if (oldPassword != Md5Hashing.md5(oldTypedPassword)) {
                throw new AuthenticationHelperException("Wrong password typed!");
            }
            userService.resetPassword(currentUser.getPersonalInfo().getEmail(), newPassword);
        } catch (AuthenticationHelperException e) {
            //bindingResult.rejectValue("password", "auth_error", e.getMessage());
            return "register";
        }
        return "profile";
    }

}


