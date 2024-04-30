package com.example.platform.Task.IndividualTask;

import com.example.platform.Task.Task;
import com.example.platform.course.Course;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class IndividualTask extends Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    private Date date;
    private Date deadline;
    private String contents;

    public IndividualTask(Course course, Date date, Date deadline, String contents) {
        this.course = course;
        this.date = date;
        this.deadline = deadline;
        this.contents = contents;
    }

    public IndividualTask() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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

    public IndividualTask(Long id, Course course, Date date, Date deadline, String contents) {
        this.id = id;
        this.course = course;
        this.date = date;
        this.deadline = deadline;
        this.contents = contents;
    }
}
