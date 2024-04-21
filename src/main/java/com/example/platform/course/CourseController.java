package com.example.platform.course;


import com.example.platform.User.ChangePasswordRequest;
import com.example.platform.User.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<CourseDTO> GetById(@PathVariable Long id){
        return service.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping()
    public ResponseEntity<List<CourseDTO>> findAllCourses() {
        return ResponseEntity.ok(service.findAllCourses());
    }

    @PostMapping
    public ResponseEntity<?> saveCourse(
            @RequestBody SaveCourseDTO request,
            Principal connectedUser
    ) {
        service.createCourse(request, connectedUser);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        service.deleteCourse(id);
        return ResponseEntity.noContent().build();}

}
