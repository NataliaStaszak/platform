package com.example.platform.Task;

import com.example.platform.Task.GroupTask.CreateGroupTaskRequest;
import com.example.platform.Task.GroupTask.GroupTaskDTO;
import com.example.platform.Task.GroupTask.GroupTaskService;
import com.example.platform.Task.IndividualTask.IndividualTaskDTO;
import com.example.platform.Task.IndividualTask.IndividualTaskService;
import com.example.platform.Task.IndividualTask.SaveIndividualTaskRequest;
import org.apache.catalina.Group;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private IndividualTaskService individualTaskService;
    private GroupTaskService groupTaskService;

    public TaskController(IndividualTaskService individualTaskService, GroupTaskService groupTaskService) {
        this.individualTaskService = individualTaskService;
        this.groupTaskService = groupTaskService;
    }


    @PostMapping
    public ResponseEntity<?> saveTask(@RequestBody SaveIndividualTaskRequest request) {
        individualTaskService.createIndividualTask(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("task/{id}")
    @ResponseBody
    public ResponseEntity<IndividualTaskDTO> GetById(@PathVariable Long id) {
        return individualTaskService.getIndividualTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<List<IndividualTaskDTO>> findAllfromCourse(@PathVariable Long id) {
        return ResponseEntity.ok(individualTaskService.findAllIndividualTaskfromCourse(id));
    }

    @GetMapping("/myTasks")
    public ResponseEntity<List<IndividualTaskDTO>> findAllTaskofUser(Principal connectedUser) {
        return ResponseEntity.ok(individualTaskService.findAllOfUser(connectedUser));
    }

    @GetMapping("/myTasksAdmin")
    public ResponseEntity<List<IndividualTaskDTO>> findAllTaskofAdmin(Principal connectedUser) {
        return ResponseEntity.ok(individualTaskService.findAllOfAdmin(connectedUser));
    }

    @PatchMapping()
    public ResponseEntity<?> changeDeadline(
            @RequestBody DeadlineChangeRequest request
    ) {
        System.out.println(request.getTaskId());
        System.out.println(request.getNewDeadline());
        individualTaskService.changeDeadline(request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTask(@PathVariable Long id) {
        individualTaskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }


    //GROUP
    @PostMapping("/createGroupTask")
    public ResponseEntity<?> saveTask(@RequestBody CreateGroupTaskRequest request) {
        groupTaskService.createGroupTask(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("task/{id}")
    ResponseEntity<?> deleteTeam(@PathVariable Long id) {
        groupTaskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("grouptask/{id}")
    @ResponseBody
    public ResponseEntity<GroupTaskDTO> GetGroupTaskById(@PathVariable Long id) {
        return groupTaskService.getGroupTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/courseGroup/{id}")
    public ResponseEntity<List<GroupTaskDTO>> findAllGroupTasksfromCourse(@PathVariable Long id) {
        return ResponseEntity.ok(groupTaskService.findAllGroupTaskfromCourse(id));
    }

    @GetMapping("/myGroupTasks")
    public ResponseEntity<List<GroupTaskDTO>> findAllGroupTaskofUser(Principal connectedUser) {
        return ResponseEntity.ok(groupTaskService.findAllOfUser(connectedUser));
    }

    @GetMapping("/myGroupTasksAdmin")
    public ResponseEntity<List<GroupTaskDTO>> findAllGroupTaskofAdmin(Principal connectedUser) {
        return ResponseEntity.ok(groupTaskService.findAllOfAdmin(connectedUser));
    }

    @PatchMapping("/group")
    public ResponseEntity<?> changeGroupTaskDeadline(
            @RequestBody DeadlineChangeRequest request
    ) {

        groupTaskService.changeDeadline(request);

        return ResponseEntity.ok().build();
    }
}
