package com.example.platform.Task.GroupTask;

import com.example.platform.course.Course;
import jakarta.persistence.OneToMany;

import java.util.Date;
import java.util.List;

public class CreateGroupTaskRequest {
    private Long courseId;
    private Date deadline;
    private String contents;
    private List<List<Long>> teams;

    public CreateGroupTaskRequest() {
    }

    public CreateGroupTaskRequest(Long courseId, Date deadline, String contents, List<List<Long>> teams) {
        this.courseId = courseId;
        this.deadline = deadline;
        this.contents = contents;
        this.teams = teams;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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

    public List<List<Long>> getTeams() {
        return teams;
    }

    public void setTeams(List<List<Long>> teams) {
        this.teams = teams;
    }
}
