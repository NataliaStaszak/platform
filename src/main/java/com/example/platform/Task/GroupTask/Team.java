package com.example.platform.Task.GroupTask;

import com.example.platform.User.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private List<User> members;

    public Team() {
    }

    public Team(Long id, List<User> members) {
        this.id = id;
        this.members = members;
    }

    public Team(List<User> members) {
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
