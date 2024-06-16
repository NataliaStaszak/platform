package com.example.platform.User;

import com.example.platform.course.CourseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService service) {
        this.service = service;
    }

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        logger.info("Changed password for user {}", connectedUser.getName());
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        logger.info("Deleted user {}", id);
        return ResponseEntity.noContent().build();}


    @GetMapping()
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        var result = service.findAllUsers();
        logger.info("Returned {} number of users in database", result.size());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<UserDTO> GetById(@PathVariable Long id){
        return service.getUserDTOById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    @ResponseBody
    public ResponseEntity<UserDTO> GetByEmail(@PathVariable String email){
        return service.getUserDTOByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/current")
    @ResponseBody
    public ResponseEntity<CurrentUserDTO> GetCurrent(Principal connectedUser){
        return  ResponseEntity.ok(service.getCurrent(connectedUser));
    }

}
