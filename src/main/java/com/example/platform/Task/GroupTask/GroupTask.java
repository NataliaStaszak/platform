package com.example.platform.Task.GroupTask;

import com.example.platform.course.Course;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class GroupTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    private Date date;
    private Date deadline;
    private String contents;
    @OneToMany
    private List<Team> teams;

    public GroupTask() {
    }

    public GroupTask(Long id, Course course, Date date, Date deadline, String contents, List<Team> teams) {
        this.id = id;
        this.course = course;
        this.date = date;
        this.deadline = deadline;
        this.contents = contents;
        this.teams = teams;
    }

    public GroupTask(Course course, Date date, Date deadline, String contents, List<Team> teams) {
        this.course = course;
        this.date = date;
        this.deadline = deadline;
        this.contents = contents;
        this.teams = teams;
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

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
