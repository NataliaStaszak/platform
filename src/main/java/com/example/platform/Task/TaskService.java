package com.example.platform.Task;

import com.example.platform.Task.GroupTask.GroupTaskService;
import com.example.platform.Task.IndividualTask.IndividualTaskService;
import com.example.platform.Task.IndividualTask.IndividualTaskNotSolvedReport;
import com.example.platform.User.User;
import com.example.platform.course.CourseService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
public class TaskService {
    private IndividualTaskService individualTaskService;
    private GroupTaskService groupTaskService;
    private CourseService courseService;

    public TaskService(IndividualTaskService individualTaskService, GroupTaskService groupTaskService,CourseService courseService) {
        this.individualTaskService = individualTaskService;
        this.groupTaskService = groupTaskService;
        this.courseService=courseService;
    }

    public List<TaskNotSolvedReport> reportUnsolved(Principal connectedUser) {
        List<TaskNotSolvedReport> reports = new ArrayList<>();
        reports.addAll(groupTaskService.findAllUnsolvedTasksOfAdmin(connectedUser));
        reports.addAll(individualTaskService.findAllUnsolvedTasksOfAdmin(connectedUser));
        return reports;
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

    public boolean isUserMemberOrAdmin(Principal connectedUser,Long id) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if(courseService.isUserMember(id,user)||courseService.isUserAuthor(id,user))
            return true;
        return false;
    }

    public List<TaskDTO> findAllOfUserFromCourse(Principal connectedUser, Long id) {
        List<TaskDTO> list = new ArrayList<>();
        System.out.println("service: "+ id);
        list.addAll(individualTaskService.findAllOfUserFromCourse(connectedUser,id));
        list.addAll(groupTaskService.findAllOfUserFromCourse(connectedUser,id));
        Collections.sort(list, Comparator.comparing(TaskDTO::getDate));
        return list;
    }

}
