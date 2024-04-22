package com.example.platform.course;

import com.example.platform.User.User;
import com.example.platform.User.UserDTO;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class InviteDTO {
    Long id;

    private UserDTO user;

    private CourseDTO course;

    public InviteDTO() {
    }

    public InviteDTO(Long id, UserDTO user, CourseDTO course) {
        this.id = id;
        this.user = user;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }
}
