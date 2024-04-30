package com.example.platform.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


public class Dog extends Animal {
    private Long id;
    private String name;

    public Dog() {
        super();

    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dog(Long id, String name,Double weight) {
        super(weight);
        this.id = id;
        this.name = name;

    }
}
