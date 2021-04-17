package com.java.smart_garage.controllers.mvc;

import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.configuration.Md5Hashing;
import com.java.smart_garage.contracts.serviceContracts.UserService;
import com.java.smart_garage.exceptions.AuthenticationHelperException;
import com.java.smart_garage.models.Credential;
import com.java.smart_garage.models.PersonalInfo;
import com.java.smart_garage.models.User;
import com.java.smart_garage.models.UserType;
import com.java.smart_garage.models.dto.PersonalInfoDto;
import com.java.smart_garage.models.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping
public class AuthenticationMvcController {

    private final AuthenticationHelper authenticationHelper;
    private final UserService userService;
    private final ModelConversionHelper modelConversionHelper;


    @Autowired
    public AuthenticationMvcController(AuthenticationHelper authenticationHelper,
                                       UserService userService,
                                       ModelConversionHelper modelConversionHelper) {
        this.authenticationHelper = authenticationHelper;
        this.userService = userService;
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

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@Valid @ModelAttribute("loginDto") LoginDto dto,
                              BindingResult bindingResult,
                              HttpSession session) {

        if(bindingResult.hasErrors()) {
            return "login";
        }

        try {
            authenticationHelper.verifyAuthentication(dto.getUsername(), dto.getPassword());
            session.setAttribute("currentUserUsername", dto.getUsername());
        } catch (AuthenticationHelperException e) {
            bindingResult.rejectValue("username", "auth_error", e.getMessage());
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.removeAttribute("currentUserUsername");
        return "redirect:/";
    }

    @GetMapping("/register")
    public String showRegisterPage(HttpSession session) {
        session.setAttribute("registerDto", new PersonalInfoDto());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(PersonalInfoDto dto,
                                 BindingResult bindingResult,
                                 HttpSession session) {

        try {
            User user = new User();
            PersonalInfo p = modelConversionHelper.personalInfoFromDto(dto);
            user.setPersonalInfo(p);
            Credential credential = new Credential();
            credential.setUsername(dto.getFirstName() + "." + dto.getLastName());  //todo s mail
            String password = Md5Hashing.generateNewPassword();
            credential.setPassword(Md5Hashing.md5(password));
            user.setCredential(credential);
            UserType usertype = new UserType();
            usertype.setType("Customer");
            user.setUserType(usertype);
            userService.create(user, authenticationHelper.tryGetUser(session));
        } catch (AuthenticationHelperException e) {
            bindingResult.rejectValue("username", "auth_error", e.getMessage());
            return "register";
        }
        return "redirect:/login";
    }


}
