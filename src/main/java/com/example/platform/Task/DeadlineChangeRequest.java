package com.example.platform.Task;

import java.util.Date;

public class DeadlineChangeRequest {
    private Long taskId;
    private Date newDeadline;

    public DeadlineChangeRequest() {
    }

    public DeadlineChangeRequest(Long taskId, Date newDeadline) {
        this.taskId = taskId;
        this.newDeadline = newDeadline;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Date getNewDeadline() {
        return newDeadline;
    }

    public void setNewDeadline(Date newDeadline) {
        this.newDeadline = newDeadline;
    }
}
