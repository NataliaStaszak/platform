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
    private TaskService taskService;

    public TaskController(IndividualTaskService individualTaskService, GroupTaskService groupTaskService, TaskService taskService) {
        this.individualTaskService = individualTaskService;
        this.groupTaskService = groupTaskService;
        this.taskService = taskService;
    }

    @PostMapping("/createIndividualTask")
    public ResponseEntity<?> saveTask(@RequestBody SaveIndividualTaskRequest request) {
        individualTaskService.createIndividualTask(request);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/createGroupTask")
    public ResponseEntity<?> saveTask(@RequestBody CreateGroupTaskRequest request) {
        groupTaskService.createGroupTask(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("individualTask/{id}")
    @ResponseBody
    public ResponseEntity<IndividualTaskDTO> GetById(@PathVariable Long id) {
        return individualTaskService.getIndividualTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("groupTask/{id}")
    @ResponseBody
    public ResponseEntity<GroupTaskDTO> GetGroupTaskById(@PathVariable Long id) {
        return groupTaskService.getGroupTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<List<TaskDTO>> findAllfromCourse(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.findAllIndividualTaskfromCourse(id));
    }

    @GetMapping("/myTasks")
    public ResponseEntity<List<TaskDTO>> findAllTaskofUser(Principal connectedUser) {
        return ResponseEntity.ok(taskService.findAllOfUser(connectedUser));
    }

    @GetMapping("/myTasksAdmin")
    public ResponseEntity<List<TaskDTO>> findAllTaskofAdmin(Principal connectedUser) {
        return ResponseEntity.ok(taskService.findAllOfAdmin(connectedUser));
    }

    @PatchMapping()
    public ResponseEntity<?> changeDeadline(
            @RequestBody DeadlineChangeRequest request
    ) {
        taskService.changeDeadline(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    ResponseEntity<?> deleteTask(@RequestBody TaskDeleteRequest request) {
        taskService.delete(request);
        return ResponseEntity.noContent().build();
    }





}
