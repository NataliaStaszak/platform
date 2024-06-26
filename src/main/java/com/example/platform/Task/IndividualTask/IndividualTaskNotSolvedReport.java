package com.example.platform.Task.IndividualTask;

import com.example.platform.Task.TaskDTO;
import com.example.platform.Task.TaskNotSolvedReport;
import com.example.platform.User.UserDTO;

import java.util.List;

public class IndividualTaskNotSolvedReport extends TaskNotSolvedReport {
    private List<UserDTO> users;

    public IndividualTaskNotSolvedReport(TaskDTO taskDTO, List<UserDTO> users) {
        super(taskDTO);
        this.users = users;
    }

    public IndividualTaskNotSolvedReport() {
    }


    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
