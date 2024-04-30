package com.example.platform.Task;

import com.example.platform.Task.GroupTask.GroupTask;
import com.example.platform.Task.GroupTask.GroupTaskDTO;
import com.example.platform.Task.GroupTask.GroupTaskService;
import com.example.platform.Task.IndividualTask.IndividualTaskDTO;
import com.example.platform.Task.IndividualTask.IndividualTaskService;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private IndividualTaskService individualTaskService;
    private GroupTaskService groupTaskService;

    public TaskService(IndividualTaskService individualTaskService, GroupTaskService groupTaskService) {
        this.individualTaskService = individualTaskService;
        this.groupTaskService = groupTaskService;
    }

    public List<TaskDTO> findAllIndividualTaskfromCourse(Long id) {
        List<TaskDTO> list = new ArrayList<>();
        list.addAll(individualTaskService.findAllIndividualTaskfromCourse(id));
        list.addAll(groupTaskService.findAllGroupTaskfromCourse(id));
        Collections.sort(list, Comparator.comparing(TaskDTO::getDate));
        return list;
    }

    public List<TaskDTO> findAllOfUser(Principal connectedUser) {
        List<TaskDTO> list = new ArrayList<>();
        list.addAll(individualTaskService.findAllOfUser(connectedUser));
        list.addAll(groupTaskService.findAllOfUser(connectedUser));
        Collections.sort(list, Comparator.comparing(TaskDTO::getDate));
        return list;
    }

    public List<TaskDTO> findAllOfAdmin(Principal connectedUser) {
        List<TaskDTO> list = new ArrayList<>();
        list.addAll(individualTaskService.findAllOfAdmin(connectedUser));
        list.addAll(groupTaskService.findAllOfAdmin(connectedUser));
        Collections.sort(list, Comparator.comparing(TaskDTO::getDate));
        return list;
    }

    public void changeDeadline(DeadlineChangeRequest request) {
        if(request.getIsIndividual())
            individualTaskService.changeDeadline(request);
        else
            groupTaskService.changeDeadline(request);
    }

    public void delete(TaskDeleteRequest request) {
        if(request.getIsIndividual()) {
            individualTaskService.deleteTask(request.getTaskId());

        }
        else
            groupTaskService.deleteTask(request.getTaskId());

    }
}
