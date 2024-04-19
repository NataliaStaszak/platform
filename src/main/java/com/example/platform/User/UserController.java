package com.example.platform.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity<String> test3() {
        return ResponseEntity.ok("password");
    }

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

}
