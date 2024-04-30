package com.example.platform.Task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class DeadlineChangeRequest {
    private Long taskId;
    private Date newDeadline;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Boolean isIndividual;

    public DeadlineChangeRequest() {
    }

    public DeadlineChangeRequest(Long taskId, Date newDeadline, Boolean isIndividual) {
        this.taskId = taskId;
        this.newDeadline = newDeadline;
        this.isIndividual = isIndividual;
    }

    public Boolean getIsIndividual() {
        return isIndividual;
    }

    public void setIsIndividual(Boolean isIndividual) {
        this.isIndividual = isIndividual;
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

    public static class TaskDTO {
    }
}
