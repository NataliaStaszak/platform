package com.example.platform.IndividualTask;

import com.example.platform.course.CourseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class IndividualTaskController {
    private IndividualTaskService service;

    public IndividualTaskController(IndividualTaskService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> saveTask(@RequestBody SaveIndividualTaskRequest request) {
        service.createIndividualTask(request);
        return ResponseEntity.ok().build();
    }
    @GetMapping("task/{id}")
    @ResponseBody
    public ResponseEntity<IndividualTaskDTO> GetById(@PathVariable Long id){
        return service.getIndividualTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<List<IndividualTaskDTO>> findAllfromCourse(@PathVariable Long id) {
        return ResponseEntity.ok(service.findAllIndividualTaskfromCourse(id));
    }
    @GetMapping("/myTasks")
    public ResponseEntity<List<IndividualTaskDTO>> findAllTaskofUser(Principal connectedUser) {
        return ResponseEntity.ok(service.findAllOfUser(connectedUser));
    }
    @GetMapping("/myTasksAdmin")
    public ResponseEntity<List<IndividualTaskDTO>> findAllTaskofAdmin(Principal connectedUser) {
        return ResponseEntity.ok(service.findAllOfAdmin(connectedUser));
    }
    @PatchMapping()
    public ResponseEntity<?> changePassword(
            @RequestBody DeadlineChangeRequest request
    ) {
        System.out.println(request.getTaskId());
        System.out.println(request.getNewDeadline());
        service.changeDeadline(request);

        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();}
}
