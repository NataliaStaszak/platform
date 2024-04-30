package com.example.platform.Task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TaskDeleteRequest {
    private Long taskId;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Boolean isIndividual;

    public TaskDeleteRequest() {
    }

    public TaskDeleteRequest(Long taskId, Boolean isIndividual) {
        this.taskId = taskId;
        this.isIndividual = isIndividual;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Boolean getIsIndividual() {
        return isIndividual;
    }

    public void setIsIndividual(Boolean isIndividual) {
        this.isIndividual = isIndividual;

    }
}
