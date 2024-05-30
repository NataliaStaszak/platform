package com.example.platform.auth;

import com.example.platform.User.UserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        AuthenticationResponse response=authenticationService.register((request));
        System.out.println("response"+response.getToken().toString());
        if (response.getToken().equals("Email already in database")) {
            logger.warn("Cannot register this user: Email is already in database!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        else
        {
            logger.info("Created new user. Authentication success.");
            return ResponseEntity.ok(response);
        }

    }
    @PostMapping("/registerNewAdmin")
    public ResponseEntity<AuthenticationResponse> registerAdmin(
            @RequestBody RegisterRequest request
    ){
        AuthenticationResponse response=authenticationService.registerAdmin((request));
        System.out.println("response"+response.getToken().toString());
        if (response.getToken().equals("Email already in database")) {
            logger.warn("Cannot register this admin: Email is already in database!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        else {
            logger.warn("Created new admin. Authentication success");
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request){
        logger.info("User {} successfully authenticated.", request.getEmail());
        return ResponseEntity.ok(authenticationService.authenticate((request)));
    }
}
