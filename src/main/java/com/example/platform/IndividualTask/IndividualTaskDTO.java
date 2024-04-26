package com.example.platform.IndividualTask;

import com.example.platform.course.Course;
import com.example.platform.course.CourseDTO;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

public class IndividualTaskDTO {
    Long id;
    String courseName;
    Date date;
    Date deadline;
    String contents;

    public IndividualTaskDTO() {
    }

    public IndividualTaskDTO(Long id, String courseName, Date date, Date deadline, String contents) {
        this.id = id;
        this.courseName = courseName;
        this.date = date;
        this.deadline = deadline;
        this.contents = contents;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
