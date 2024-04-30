package com.example.platform.Task.GroupTask;

import com.example.platform.Task.TaskDTO;

import java.util.Date;
import java.util.List;

public class GroupTaskDTO extends TaskDTO {
    private List<TeamDTO> members;

    public GroupTaskDTO(Long id,String courseName,Date date,Date deadline,String contents,List<TeamDTO> members) {
        super(id,courseName,date,deadline,contents);
        this.members = members;
    }

    public GroupTaskDTO() {
    }

    public List<TeamDTO> getMembers() {
        return members;
    }

    public void setMembers(List<TeamDTO> members) {
        this.members = members;
    }

}
