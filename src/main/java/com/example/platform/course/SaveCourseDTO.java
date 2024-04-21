package com.example.platform.course;

public class SaveCourseDTO {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SaveCourseDTO(String name) {
        this.name = name;
    }

    public SaveCourseDTO() {
    }
}
