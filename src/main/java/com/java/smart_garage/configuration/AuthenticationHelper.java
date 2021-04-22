package com.java.smart_garage.configuration;

import com.java.smart_garage.contracts.serviceContracts.CredentialService;
import com.java.smart_garage.contracts.serviceContracts.UserService;
import com.java.smart_garage.exceptions.AuthenticationHelperException;
import com.java.smart_garage.exceptions.EntityNotFoundException;
import com.java.smart_garage.exceptions.UnauthorizedOperationException;
import com.java.smart_garage.models.Credential;
import com.java.smart_garage.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class AuthenticationHelper {

    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private final CredentialService credentialService;
    private final UserService userService;

    @Autowired
    public AuthenticationHelper(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    public User tryGetUser(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION_HEADER_NAME)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "The request resource requires authorization.");
        }

        try {
            String username = headers.getFirst(AUTHORIZATION_HEADER_NAME);
            return userService.getByUserName(username);
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
            return userService.getByUserName(currentUserUsername);
        } catch (EntityNotFoundException e) {
            throw new UnauthorizedOperationException("No logged in user.");
        }
    }

    public User verifyAuthorization(HttpSession session, String role) {


        User user = tryGetUser(session);
        String userRoles = user.getUserType().getType();

        if (!userRoles.equalsIgnoreCase(role)) {
            throw new UnauthorizedOperationException("User does not have the required authorization.");
        }

        return user;

    }

    public Credential verifyAuthentication(String username, String password){
        try{
            Credential credential = credentialService.getByUsername(username);
            String hashedPassword = Md5Hashing.md5(password);
            if(!credential.getPassword().equals(hashedPassword)){
                throw new AuthenticationHelperException("Wrong user/password.");
            }

            return credential;
        } catch (EntityNotFoundException e){
            throw new AuthenticationHelperException("Wrong user/password.");
        }
    }

    public User convertCredentialToUser(Credential credential) {
        List<User> filteredUser = userService.getAllUsers().stream().
                filter(u -> u.getCredential().equals(credential))
                .collect(Collectors.toList());
        return filteredUser.get(0);
    }
}
