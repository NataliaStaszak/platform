package com.example.platform.auth;


import com.example.platform.User.Role;
import com.example.platform.User.UserRepository;
import com.example.platform.config.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.platform.User.User;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public AuthenticationResponse register(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail()))
            return new AuthenticationResponse("Email already in database");
        User user = new User(
               request.getFirstName(),
               request.getLastName(),
               request.getEmail(),
               passwordEncoder.encode(request.getPassword()),
               Role.USER
       );
       userRepository.save(user);
       String jwtToken=jwtService.generateToken(user);
       return new AuthenticationResponse(jwtToken);

    }
    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail()))
            return new AuthenticationResponse("Email already in database");
        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                Role.ADMIN
        );
        userRepository.save(user);
        String jwtToken=jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken=jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken);


    }
}
