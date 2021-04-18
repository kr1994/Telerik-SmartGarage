package com.java.smart_garage.controllers.mvc;

import com.java.smart_garage.ModelMaper.ModelConversionHelper;
import com.java.smart_garage.configuration.AuthenticationHelper;
import com.java.smart_garage.configuration.Md5Hashing;
import com.java.smart_garage.contracts.serviceContracts.EmailService;
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
    private final EmailService emailService;

    @Autowired
    public AuthenticationMvcController(AuthenticationHelper authenticationHelper,
                                       UserService userService,
                                       ModelConversionHelper modelConversionHelper, EmailService emailService) {
        this.authenticationHelper = authenticationHelper;
        this.userService = userService;
        this.modelConversionHelper = modelConversionHelper;
        this.emailService = emailService;
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
    public String handleRegisterCustomer(PersonalInfoDto dto,
                                         BindingResult bindingResult,
                                         HttpSession session) {

        try {
            User user = fillUser(dto, "Customer");
            userService.create(user, authenticationHelper.tryGetUser(session));
            emailService.sendMailForCredentials(user.getPersonalInfo().getEmail(),
                                                user.getCredential().getUsername(),
                                                user.getCredential().getPassword());
        } catch (AuthenticationHelperException e) {
            bindingResult.rejectValue("username", "auth_error", e.getMessage());
            return "register";
        }
        return "redirect:/login";
    }

    /*
    @GetMapping("/register_employee")
    public String createEmployee(HttpSession session) {
        session.setAttribute("registerDto", new PersonalInfoDto());
        return "register_employee";
    }
    */

    @PostMapping("/register_employee")
    public String handleRegisterEmployee(BindingResult bindingResult,
                                         HttpSession session) {

        try {
            PersonalInfoDto dto = new PersonalInfoDto();
            dto.setFirstName("Employee" + Md5Hashing.generateNewPassword(4));
            dto.setLastName("Employer" + Md5Hashing.generateNewPassword(2));
            dto.setEmail("empemp" + Md5Hashing.generateNewPassword(3) + "@gmail.com");
            dto.setPhoneNumber("08" + Md5Hashing.generateNewPhonenumber());
            User user = fillUser(dto, "Employee");
            userService.create(user, authenticationHelper.tryGetUser(session));
            emailService.sendMailForCredentials(user.getPersonalInfo().getEmail(),
                                                user.getCredential().getUsername(),
                                                user.getCredential().getPassword());
        } catch (AuthenticationHelperException e) {
            bindingResult.rejectValue("username", "auth_error", e.getMessage());
            return "register";
        }
        return "redirect:/login";
    }

    private String extractUsernameFromEmail(String email) {
        int atIndex = email.indexOf("@");
        String username = email.substring(0, atIndex);
        username += Md5Hashing.generateNewPassword(3);
        return username;
    }

    private User fillUser(PersonalInfoDto dto, String role) {
        User user = new User();
        PersonalInfo p = modelConversionHelper.personalInfoFromDto(dto);
        user.setPersonalInfo(p);
        Credential credential = new Credential();
        credential.setUsername(extractUsernameFromEmail(user.getPersonalInfo().getEmail()));
        String password = Md5Hashing.generateNewPassword(8);
        credential.setPassword(Md5Hashing.md5(password));
        user.setCredential(credential);
        UserType usertype = new UserType();
        usertype.setType(role);
        user.setUserType(usertype);
        return user;
    }

}
