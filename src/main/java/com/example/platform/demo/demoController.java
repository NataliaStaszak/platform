package com.example.platform.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/demo")
public class demoController {


    @GetMapping("/secure")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("sekjur");
    }
    @GetMapping("/all")
    public ResponseEntity<String> test2() {
        return ResponseEntity.ok("sekjur");
    }
    @GetMapping("/admin")
    public ResponseEntity<String> test3() {
        return ResponseEntity.ok("sekjur");
    }

}
