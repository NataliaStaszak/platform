package com.example.platform.Task.GroupTask;

import java.util.List;

public class CreateTeamRequest {
    private List<Long> ids;

    public CreateTeamRequest(List<Long> ids) {
        this.ids = ids;
    }

    public CreateTeamRequest() {
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
