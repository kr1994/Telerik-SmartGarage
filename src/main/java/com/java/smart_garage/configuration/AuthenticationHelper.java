package com.java.smart_garage.configuration;


import com.java.smart_garage.contracts.serviceContracts.UserService;
import com.java.smart_garage.exceptions.AuthenticationHelperException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;


@Component
public class AuthenticationHelper {

    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private final UserService userService;

    @Autowired
    public AuthenticationHelper(UserService userService) {
        this.userService = userService;
    }

    public User tryGetUser(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION_HEADER_NAME)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "The request resource requires authorization.");
        }

        try {
            String username = headers.getFirst(AUTHORIZATION_HEADER_NAME);
            return userService.getByUsername(username);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username");
        }
    }
    public User tryGetUser(HttpSession session) {
        String currentUserUsername = (String) session.getAttribute("currentUserUsername");

        if (currentUserUsername == null) {
            throw new UnauthorizedOperationException("No logged in user.");
        }

        try {
            return userService.getByUsername(currentUserUsername);
        } catch (EntityNotFoundException e) {
            throw new UnauthorizedOperationException("No logged in user.");
        }
    }

    public User verifyAuthorization(HttpSession session, String role) {
        User user = tryGetUser(session);

        String userRoles = user.getUserType().getType();

        if (!userRoles.equalsIgnoreCase(role)) {
            throw new UnauthorizedOperationException("Users does not have the required authorization.");
        }

        return user;
    }

    public User verifyAuthentication(String username,String password){
        try{
            User user = userService.getByUsername(username);

            if(!user.getPassword().equals(password)){
                throw new AuthenticationHelperException("Wrong user/password.");
            }

            return user;
        }catch (EntityNotFoundException e){
            throw new AuthenticationHelperException("Wrong user/password.");
        }
    }
}
