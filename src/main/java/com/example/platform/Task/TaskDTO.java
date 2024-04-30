package com.example.platform.Task;

import java.util.Date;

public class TaskDTO {
    private Long id;
    private String courseName;
    private Date date;
    private Date deadline;
    private String contents;

    public TaskDTO(Long id, String courseName, Date date, Date deadline, String contents) {
        this.id = id;
        this.courseName = courseName;
        this.date = date;
        this.deadline = deadline;
        this.contents = contents;
    }

    public TaskDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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
