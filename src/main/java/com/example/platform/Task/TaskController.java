package com.example.platform.Task;

import com.example.platform.Task.GroupTask.CreateGroupTaskRequest;
import com.example.platform.Task.GroupTask.GroupTaskDTO;
import com.example.platform.Task.GroupTask.GroupTaskService;
import com.example.platform.Task.IndividualTask.IndividualTaskDTO;
import com.example.platform.Task.IndividualTask.IndividualTaskService;
import com.example.platform.Task.IndividualTask.SaveIndividualTaskRequest;
import com.example.platform.Task.IndividualTask.IndividualTaskNotSolvedReport;
import com.example.platform.User.User;
import com.example.platform.course.CourseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private IndividualTaskService individualTaskService;
    private GroupTaskService groupTaskService;
    private TaskService taskService;
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    public TaskController(IndividualTaskService individualTaskService, GroupTaskService groupTaskService, TaskService taskService) {
        this.individualTaskService = individualTaskService;
        this.groupTaskService = groupTaskService;
        this.taskService = taskService;
    }

    @PostMapping("/createIndividualTask")
    public ResponseEntity<?> saveTask(@RequestBody SaveIndividualTaskRequest request) {
        individualTaskService.createIndividualTask(request);
        logger.info("Created individual task {}", request.getContents());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/createGroupTask")
    public ResponseEntity<?> saveTask(@RequestBody CreateGroupTaskRequest request) {
        groupTaskService.createGroupTask(request);
        logger.info("Created group task {}", request.getContents());
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
    public ResponseEntity<GroupTaskDTO> GetGroupTaskById(@PathVariable Long id,Principal connectedUser) {
        return groupTaskService.getGroupTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<List<TaskDTO>> findAllfromCourse(@PathVariable Long id,Principal connectedUser) {

        var result = taskService.findAllIndividualTaskfromCourse(id);
        if(taskService.isUserMemberOrAdmin(connectedUser,id)) {
            logger.info("Found {} number of tasks for course with id {}", result.size(), id);
            return ResponseEntity.ok(result);
        }
        else {
            logger.warn("Cannot return tasks for course with id {}", id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/myTasks")
    public ResponseEntity<List<TaskDTO>> findAllTaskofUser(Principal connectedUser) {
        var result = taskService.findAllOfUser(connectedUser);
        logger.info("Found {} number of tasks for user {}", result.size(), connectedUser.getName());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/myTasksAdmin")
    public ResponseEntity<List<TaskDTO>> findAllTaskofAdmin(Principal connectedUser) {
        var result = taskService.findAllOfAdmin(connectedUser);
        logger.info("Found {} number of tasks for admin {}", result.size(), connectedUser.getName());
        return ResponseEntity.ok(result);
    }
    @GetMapping("/myUnsolvedTasksAdmin")
    public ResponseEntity<List<TaskNotSolvedReport>> findAllUnsolvedTaskofAdmin(Principal connectedUser) {
        var result = taskService.reportUnsolved(connectedUser);
        logger.info("Found {} number of unsolved tasks for admin {}", result.size(), connectedUser.getName());
        return ResponseEntity.ok(result);
    }

    @PatchMapping()
    public ResponseEntity<?> changeDeadline(@RequestBody DeadlineChangeRequest request) {
        taskService.changeDeadline(request);
        logger.info("Successfully changed deadline for task {}", request.getTaskId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    ResponseEntity<?> deleteTask(@RequestBody TaskDeleteRequest request) {
        taskService.delete(request);
        logger.info("Successfully deleted deadline for task {}", request.getTaskId());
        return ResponseEntity.noContent().build();
    }
}
