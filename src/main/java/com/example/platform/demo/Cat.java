package com.example.platform.demo;

public class Cat extends Animal {
    private Long id;
    private String name;
    private String meow;

    public Cat() {
        super();
    }

    public Cat(Long id, String name, String meow, Double weight) {
        super(weight);
        this.id = id;
        this.name = name;
        this.meow = meow;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeow() {
        return meow;
    }

    public void setMeow(String meow) {
        this.meow = meow;
    }

}
