package com.example.platform.course;


import com.example.platform.User.User;
import com.example.platform.User.UserController;
import com.example.platform.User.UserDTO;
import com.example.platform.User.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/courses")
public class CourseController {

    private CourseService courseService;
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

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
        var result = courseService.findAllCourses();
        logger.info("Returned {} number of all courses in database", result.size());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/participants/{id}")
    public ResponseEntity<List<UserDTO>> findParticipants(@PathVariable Long id) {
        var result = courseService.findAllParticipants(id);
        logger.info("Found {} number of participants in course with id: {}", result.size(), id);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/myCourses")
    public ResponseEntity<List<CourseDTO>> findParticipants(Principal connectedUser) {
        var result = courseService.findAllUserCourses(connectedUser);
        logger.info("Found {} number of courses for user : {}", result.size(), connectedUser.getName());
        return ResponseEntity.ok(result);
    }
    @GetMapping("/myCoursesAdmin")
    public ResponseEntity<List<CourseDTO>> findAdminCourses(Principal connectedUser) {
        var result = courseService.findAllAuthorCourses(connectedUser);
        logger.info("Found {} number of courses created by {}.", result.size(), connectedUser.getName());
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> saveCourse(
            @RequestBody SaveCourseDTO request,
            Principal connectedUser
    ) {
        courseService.createCourse(request, connectedUser);
        logger.info("Successfully saved course : {}", request.getName());
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        logger.info("Successfully deleted course with id: {}", id);
        return ResponseEntity.noContent().build();}







}
