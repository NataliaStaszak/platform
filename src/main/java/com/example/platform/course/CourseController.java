package com.example.platform.course;


import com.example.platform.User.User;
import com.example.platform.User.UserDTO;
import com.example.platform.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private CourseService courseService;
    private UserService userService;

    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<CourseDTO> GetById(@PathVariable Long id){
        return courseService.getCourseDTOById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping()
    public ResponseEntity<List<CourseDTO>> findAllCourses() {
        return ResponseEntity.ok(courseService.findAllCourses());
    }

    @GetMapping("/participants/{id}")
    public ResponseEntity<List<UserDTO>> findParticipants(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.findAllParticipants(id));
    }
    @GetMapping("/myCourses")
    public ResponseEntity<List<CourseDTO>> findParticipants(Principal connectedUser) {
        return ResponseEntity.ok(courseService.findAllUserCourses(connectedUser));
    }
    @GetMapping("/myCoursesAdmin")
    public ResponseEntity<List<CourseDTO>> findAdminCourses(Principal connectedUser) {
        return ResponseEntity.ok(courseService.findAllAuthorCourses(connectedUser));
    }

    @PostMapping
    public ResponseEntity<?> saveCourse(
            @RequestBody SaveCourseDTO request,
            Principal connectedUser
    ) {
        courseService.createCourse(request, connectedUser);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();}







}
