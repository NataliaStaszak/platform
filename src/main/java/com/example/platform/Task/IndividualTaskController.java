package com.example.platform.Task;

import com.example.platform.Task.GroupTask.CreateGroupTaskRequest;
import com.example.platform.Task.GroupTask.CreateTeamRequest;
import com.example.platform.Task.GroupTask.GroupTaskService;
import com.example.platform.Task.IndividualTask.IndividualTask;
import com.example.platform.Task.IndividualTask.IndividualTaskDTO;
import com.example.platform.Task.IndividualTask.IndividualTaskService;
import com.example.platform.Task.IndividualTask.SaveIndividualTaskRequest;
import com.example.platform.User.Role;
import com.example.platform.User.User;
import com.example.platform.course.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class IndividualTaskController {
    private IndividualTaskService individualTaskService;
    private GroupTaskService groupTaskService;

    public IndividualTaskController(IndividualTaskService individualTaskService, GroupTaskService groupTaskService) {
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
    public ResponseEntity<IndividualTaskDTO> GetById(@PathVariable Long id){
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
        return ResponseEntity.noContent().build();}


    @PostMapping("/createGroupTask")
    public ResponseEntity<?> saveTask(@RequestBody CreateGroupTaskRequest request) {
        groupTaskService.createGroupTask(request);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("task/{id}")
    ResponseEntity<?> deleteTeam(@PathVariable Long id) {
        groupTaskService.deleteTask(id);
        return ResponseEntity.noContent().build();}

    /*@GetMapping("/myDemo")
    public ResponseEntity<List<User>> xd() {
        User fakeAuthor = new User("Randy","Lahey","rl@cyrk.com","Cheeseburger", Role.USER);
        Course fakecourse = new Course("Fake course",fakeAuthor);
        IndividualTask fakeTask=new IndividualTask(fakecourse,new Date(),new Date(),"ShitTornado");
        List<Task> tasks=new ArrayList<>();
        tasks.add(fakeTask);
        List ids=List.of(2L,3L,6L);
        return ResponseEntity.ok();
    }*/
}
