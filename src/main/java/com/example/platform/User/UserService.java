package com.example.platform.User;

import com.example.platform.course.Course;
import com.example.platform.course.CourseDTO;
import com.example.platform.course.CourseService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }
    public void changePassword(ChangePasswordRequest request, Principal connectedUser)
    {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

    public List<UserDTO> findAllUsers() {
        List<UserDTO> users= new ArrayList<>();
        for (User user : repository.findAllUsersByRole(Role.USER))
            users.add(new UserDTO(user.getId(),user.getFirstName(), user.getLastName(), user.getEmail()));
        return users;
    }
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
    public Optional<User> getUserById(Long id){
        return repository.findById(id);
    }






}
