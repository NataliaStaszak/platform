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
        return ResponseEntity.ok("sekjur xd");
    }
    /*@GetMapping("/all")
    public ResponseEntity<String> test2() {
        return ResponseEntity.ok("sekjur xd");
    }*/
    @GetMapping("/admin")
    public ResponseEntity<String> test3() {
        return ResponseEntity.ok("sekjur xd");
    }
    @GetMapping("/all")
    public List<Object> test2() {
        List<Animal> lista = new ArrayList<>();
        Cat cat = new Cat(1L,"Fluffy","MEOW",3.4);
        Cat cat2 = new Cat(2L,"Flurry","MEoW",3.5);
        System.out.println(cat.getName()+" "+cat.getWeight());
        Dog dog = new Dog(4L,"Ricco",5.5);
        return List.of(cat,dog,cat2);
    }
}
