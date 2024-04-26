package com.example.platform.IndividualTask;

import java.util.Date;

public class SaveIndividualTaskRequest {
    Long CourseId;
    Date deadline;
    String contents;

    public SaveIndividualTaskRequest() {
    }

    public SaveIndividualTaskRequest(Long courseId, Date deadline, String contents) {
        CourseId = courseId;
        this.deadline = deadline;
        this.contents = contents;
    }

    public Long getCourseId() {
        return CourseId;
    }

    public void setCourseId(Long courseId) {
        CourseId = courseId;
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
