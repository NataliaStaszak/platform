package com.example.platform.Task.IndividualTask;

import com.example.platform.Task.TaskDTO;
import com.example.platform.User.UserDTO;

import java.util.List;

public class TaskNotSolvedReport {
    TaskDTO taskDTO;
    List<UserDTO> users;

    public TaskNotSolvedReport(TaskDTO taskDTO, List<UserDTO> users) {
        this.taskDTO = taskDTO;
        this.users = users;
    }

    public TaskNotSolvedReport() {
    }

    public TaskDTO getTaskDTO() {
        return taskDTO;
    }

    public void setTaskDTO(TaskDTO taskDTO) {
        this.taskDTO = taskDTO;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
