package com.example.platform.IndividualTask;

import com.example.platform.User.ChangePasswordRequest;
import com.example.platform.User.User;
import com.example.platform.User.UserDTO;
import com.example.platform.course.Course;
import com.example.platform.course.CourseDTO;
import com.example.platform.course.CourseRepository;
import com.example.platform.course.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class IndividualTaskService {
    private final IndividualTaskRepository repository;
    private final CourseRepository courseRepository;
    private final CourseService courseService;

    public IndividualTaskService(IndividualTaskRepository repository, CourseRepository courseRepository, CourseService courseService) {
        this.repository = repository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
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
        repository.deleteById(id);
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

    public void changeDeadline(DeadlineChangeRequest request)
    {
        repository.findById(request.getTaskId()).ifPresent(task -> {
            task.setDeadline(request.getNewDeadline());
            repository.save(task);
        });
    }


}
