package com.example.platform.Task.IndividualTask;

import com.example.platform.Task.DeadlineChangeRequest;
import com.example.platform.User.User;
import com.example.platform.User.UserDTO;
import com.example.platform.User.UserService;
import com.example.platform.course.Course;
import com.example.platform.course.CourseRepository;
import com.example.platform.course.CourseService;
import com.example.platform.resource.TaskResourceDTO;
import com.example.platform.resource.TaskResourceService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IndividualTaskService {
    private final IndividualTaskRepository repository;
    private final CourseRepository courseRepository;
    private final CourseService courseService;
    private final TaskResourceService taskResourceService;
    private final UserService userService;

    public IndividualTaskService(IndividualTaskRepository repository, CourseRepository courseRepository, CourseService courseService, TaskResourceService taskResourceService, UserService userService) {
        this.repository = repository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
        this.taskResourceService = taskResourceService;
        this.userService = userService;
    }

    public void createIndividualTask(SaveIndividualTaskRequest request){
        courseRepository.findById(request.CourseId).ifPresent(course ->
                repository.save(new IndividualTask(course, new Date(),request.getDeadline(),request.getContents()))
        );
    }

    public Optional<IndividualTaskDTO> getIndividualTaskById(Long id) {
        return repository.findById(id).map(IndividualTaskService::map);

    }

    public List<IndividualTaskDTO> findAllIndividualTaskfromCourse(Long id) {
        List<IndividualTaskDTO> tasks= new ArrayList<>();
        for (IndividualTask task : repository.getAllByCourse_Id(id))
            tasks.add(IndividualTaskService.map(task));
        return tasks;
    }

    public List<IndividualTaskDTO> findAllOfUser(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        List<Course> courses = courseRepository.findAllByAttendantsContains(user);
        List<IndividualTaskDTO> tasks= new ArrayList<>();
        for(Course course: courses){
            for(IndividualTask task :repository.getAllByCourse_Id(course.getId())){
                tasks.add(IndividualTaskService.map(task));
            }
        }
        return tasks;
    }
    public List<IndividualTaskDTO> findAllOfUserFromCourse(Principal connectedUser,Long id) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Optional<Course> course = courseRepository.findById(id);
        List<IndividualTaskDTO> tasks= new ArrayList<>();
        for(IndividualTask task :repository.getAllByCourse_Id(id)){
                tasks.add(IndividualTaskService.map(task));}
        return tasks;
    }

    public List<IndividualTaskDTO> findAllOfAdmin(Principal connectedUser) {
        List<Course> courses = courseRepository.findAllByAuthorId(
                ((User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal()).getId()
        );
        List<IndividualTaskDTO> tasks= new ArrayList<>();
        for(Course course: courses){
            for(IndividualTask task :repository.getAllByCourse_Id(course.getId())){
                tasks.add(IndividualTaskService.map(task));
            }
        }
        return tasks;
    }

    public void deleteTask(Long id) {
        System.out.println("DELETE BY ID");
        repository.deleteById(id);
    }



    public void changeDeadline(DeadlineChangeRequest request)
    {
        repository.findById(request.getTaskId()).ifPresent(task -> {
            task.setDeadline(request.getNewDeadline());
            repository.save(task);
        });
    }

    public List<IndividualTaskNotSolvedReport> findAllUnsolvedTasksOfAdmin(Principal connectedUser){
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Date now =new Date();
        List<IndividualTask> tasks = repository.getAllByDeadlineBeforeAndAndCourse_Author_Id(now,user.getId());
        List<IndividualTaskNotSolvedReport> reports=new ArrayList<>();
        for(IndividualTask task:tasks)
        {
            List<UserDTO> users=new ArrayList<>();
            List<Long>ids=getAllUsersWithoutSolution(task.getCourse().getId(),task.getId());
            for(Long id:ids)
                userService.getUserDTOById(id).ifPresent(users::add);
            reports.add(new IndividualTaskNotSolvedReport(IndividualTaskService.map(task),users));

        }
        return reports;
    }

    public List<Long> getAllUsersWithoutSolution(Long Courseid,Long TaskId){
        List<Long> membersIds=new ArrayList<>();
        for(UserDTO user:courseService.findAllParticipants(Courseid))
            membersIds.add(user.getId());
        List<Long> resourcesAuthors=new ArrayList<>();
        for(TaskResourceDTO resource:taskResourceService.getResourcesFromTask(TaskId))
        {
            resourcesAuthors.add(resource.getAuthorId());
        }
        List<Long> missingMembersIds = membersIds.stream()
                .filter(memberId -> !resourcesAuthors.contains(memberId))
                .collect(Collectors.toList());
        return missingMembersIds;

    }
    static IndividualTaskDTO map(IndividualTask task){
        IndividualTaskDTO taskDTO = new IndividualTaskDTO();

        taskDTO.setId(task.getId());
        taskDTO.setCourseName(task.getCourse().getName());
        taskDTO.setDate(task.getDate());
        taskDTO.setDeadline(task.getDeadline());
        taskDTO.setContents(task.getContents());

        return taskDTO;
    }


}
