package com.example.platform.Task.GroupTask;

import com.example.platform.User.User;
import com.example.platform.User.UserDTO;
import jakarta.persistence.ManyToMany;

import java.util.List;

public class TeamDTO {
    private Long id;
    private List<UserDTO> members;

    public TeamDTO() {

    }

    public TeamDTO(Long id, List<UserDTO> members) {
        this.id = id;
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<UserDTO> getMembers() {
        return members;
    }

    public void setMembers(List<UserDTO> members) {
        this.members = members;
    }
}
