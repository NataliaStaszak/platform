package com.example.platform.Task.IndividualTask;

import com.example.platform.Task.TaskDTO;

import java.util.Date;

public class IndividualTaskDTO extends TaskDTO {

    public IndividualTaskDTO() {
    }

    public IndividualTaskDTO(Long id, String courseName, Date date, Date deadline, String contents) {
        super(id,courseName,date,deadline,contents);
    }


}
