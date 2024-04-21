package com.example.platform.course;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/secure")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("sekjur xd");
    }

}
