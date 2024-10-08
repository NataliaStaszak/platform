package com.example.platform.resource;

import com.example.platform.Task.IndividualTask.IndividualTask;
import com.example.platform.Task.IndividualTask.IndividualTaskRepository;
import com.example.platform.User.Role;
import com.example.platform.User.User;
import com.example.platform.User.UserDTO;
import com.example.platform.User.UserRepository;
import com.example.platform.course.Course;
import com.example.platform.course.CourseDTO;
import com.example.platform.course.CourseRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskResourceService {

    private final TaskResourceRepository taskResourceRepository;
    private final UserRepository userRepository;

    public TaskResourceService(TaskResourceRepository taskResourceRepository, UserRepository userRepository, CourseRepository courseRepository, IndividualTaskRepository individualTaskRepository) {
        this.taskResourceRepository = taskResourceRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.individualTaskRepository = individualTaskRepository;
    }

    private final CourseRepository courseRepository;
    private final IndividualTaskRepository individualTaskRepository;




    public TaskResource saveTaskResource(MultipartFile file,Long id, Principal connectedUser) throws Exception {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw  new Exception("Filename contains invalid path sequence "
                        + fileName);
            }
            TaskResource taskResource= new TaskResource();
            System.out.println(id);
            Optional<IndividualTask> task=individualTaskRepository.findById(id);

            task.ifPresent(myTask->
                {
                    taskResource.setIndividualTask(myTask);
                    taskResource.setAuthor(user);
                    taskResource.setDate(new Date());
                    taskResource.setFileName(fileName);
                    taskResource.setFileType(file.getContentType());
                    try {
                        taskResource.setData(file.getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

            return taskResourceRepository.save(taskResource);

        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
    }

    public TaskResource getAttachment(String fileId) throws Exception {
        return taskResourceRepository
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }

    public void delete(String id) {
        taskResourceRepository.deleteById(id);
    }


    public List<TaskResourceDTO> getResourcesFromTaskOfUser(Long taskId, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        List<TaskResourceDTO> responseData=new ArrayList<>();
        List<TaskResource> resources = taskResourceRepository.getAllByAuthor_IdAndIndividualTask_Id(user.getId(),taskId);
        for(TaskResource resource:resources)
        {
            responseData.add(map(resource));
        }
        return responseData;
    }

    public List<TaskResourceDTO> getResourcesFromTask(Long taskId) {
        List<TaskResourceDTO> responseData=new ArrayList<>();
        List<TaskResource> resources = taskResourceRepository.getAllByIndividualTask_Id(taskId);
        for(TaskResource resource:resources)
        {
            responseData.add(map(resource));
        }
        return responseData;
    }

    public static TaskResourceDTO map(TaskResource taskResource){
        String downloadURl = "/api/v1/resources/download/"+taskResource.getId();
        return new TaskResourceDTO(taskResource.getFileName(),
                downloadURl,
                taskResource.getFileType(),
                taskResource.getDate(),
                taskResource.getAuthor().getId());
    }
}

