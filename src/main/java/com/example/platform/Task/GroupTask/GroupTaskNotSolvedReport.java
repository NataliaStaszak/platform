package com.example.platform.Task.GroupTask;

import com.example.platform.Task.GroupTask.TeamDTO;
import com.example.platform.Task.TaskDTO;
import com.example.platform.Task.TaskNotSolvedReport;
import com.example.platform.User.UserDTO;

import java.util.List;

public class GroupTaskNotSolvedReport extends TaskNotSolvedReport {

    private List<TeamDTO> teams;

    public GroupTaskNotSolvedReport(TaskDTO taskDTO, List<TeamDTO> teams) {
        super(taskDTO);
        this.teams = teams;
    }

    public GroupTaskNotSolvedReport() {
    }

    public List<TeamDTO> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamDTO> teams) {
        this.teams = teams;
    }
}
