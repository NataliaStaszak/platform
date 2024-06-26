package com.example.platform.Task;

import com.example.platform.course.CourseDTO;

public class TaskNotSolvedReport {
    private TaskDTO taskDTO;

    public TaskNotSolvedReport(TaskDTO taskDTO) {
        this.taskDTO = taskDTO;
    }

    public TaskNotSolvedReport() {
    }

    public TaskDTO getTaskDTO() {
        return taskDTO;
    }

    public void setTaskDTO(TaskDTO taskDTO) {
        this.taskDTO = taskDTO;
    }
}
