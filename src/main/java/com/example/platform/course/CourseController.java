package com.example.platform.course;


import com.example.platform.User.User;
import com.example.platform.User.UserService;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<?> saveCourse(
            @RequestBody SaveCourseDTO request,
            Principal connectedUser
    ) {
        courseService.createCourse(request, connectedUser);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/add/test/xd")
    public ResponseEntity<?> test(
    ) {
        return ResponseEntity.ok().build();
    }
    @Transactional
    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<?> addUserToCourse(
            @RequestBody UserAddRequest request
    ) {
        System.out.println(request.getUserId().toString()+request.getCourseId().toString());
        Optional<User> userToAdd= userService.getUserById(request.getUserId());
        Optional<Course> courseToJoin= courseService.getCourseById(request.getCourseId());
        courseToJoin.ifPresent( course -> {
            userToAdd.ifPresent( user ->{
                course.addAttendant(user);
                for(User i: course.getAttendants())
                {
                    System.out.println(i.getFirstName());
                }
            });
        });

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();}

}
