package com.example.platform.resource;

import com.example.platform.Task.GroupTask.GroupTask;
import com.example.platform.Task.GroupTask.GroupTaskDTO;
import com.example.platform.Task.GroupTask.GroupTaskRepository;
import com.example.platform.Task.GroupTask.Team;
import com.example.platform.Task.IndividualTask.IndividualTask;
import com.example.platform.User.User;
import com.example.platform.User.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class GroupTaskResourceService {

    private final GroupTaskResourceRepository groupTaskResourceRepository;
    private final GroupTaskRepository groupTaskRepository;

    public GroupTaskResourceService(GroupTaskResourceRepository groupTaskResourceRepository, GroupTaskRepository groupTaskRepository) {
        this.groupTaskResourceRepository = groupTaskResourceRepository;
        this.groupTaskRepository = groupTaskRepository;
    }

    public GroupTaskResource saveGroupTaskResource(MultipartFile file, Long id, Principal connectedUser) throws Exception {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Team team = findTeam(user.getId(), id);
        Optional<GroupTask> grouptask = groupTaskRepository.findById(id);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence "
                        + fileName);
            }
            GroupTaskResource taskResource = new GroupTaskResource();
            grouptask.ifPresent(myTask ->
            {
                taskResource.setGroupTask(myTask);
                taskResource.setAuthor(team);
                taskResource.setDate(new Date());
                taskResource.setFileName(fileName);
                taskResource.setFileType(file.getContentType());
                try {
                    taskResource.setData(file.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            return groupTaskResourceRepository.save(taskResource);

        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
    }

    public GroupTaskResource getAttachment(String fileId) throws Exception {
        return groupTaskResourceRepository
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }

    public void delete(String id) {
        groupTaskResourceRepository.deleteById(id);
    }


    public List<TaskResourceDTO> getResourcesFromGroupTaskOfUser(Long taskId, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Long teamId = findTeam(user.getId(), taskId).getId();
        List<TaskResourceDTO> responseData = new ArrayList<>();
        List<GroupTaskResource> resources = groupTaskResourceRepository.getAllByAuthor_IdAndGroupTask_Id(teamId, taskId);
        for (GroupTaskResource resource : resources) {
            responseData.add(map(resource));
        }
        return responseData;
    }

    public List<TaskResourceDTO> getResourcesFromGroupTask(Long taskId) {
        List<TaskResourceDTO> responseData = new ArrayList<>();
        List<GroupTaskResource> resources = groupTaskResourceRepository.getAllByGroupTask_Id(taskId);
        for (GroupTaskResource resource : resources) {
            responseData.add(map(resource));
        }
        return responseData;
    }

    public static TaskResourceDTO map(GroupTaskResource taskResource) {
        String downloadURl = "/api/v1/resources/downloadGroup/" + taskResource.getId();
        return new TaskResourceDTO(taskResource.getFileName(),
                downloadURl,
                taskResource.getFileType(),
                taskResource.getDate(),
                taskResource.getAuthor().getId());
    }

    public Team findTeam(Long userId, Long taskId) {
        Team team = new Team();

        Optional<GroupTask> grouptask = groupTaskRepository.findById(taskId);
        grouptask.ifPresent(task ->
        {
            for (Team thisTeam : task.getTeams()) {
                for (User thisUser : thisTeam.getMembers()) {
                    if (thisUser.getId() == userId) {
                        team.setId(thisTeam.getId());
                        team.setMembers(thisTeam.getMembers());
                    }
                }
            }
        });
        return team;
    }
    public List<TaskResourceDTO> getResourcesFromTask(Long taskId) {
        List<TaskResourceDTO> responseData=new ArrayList<>();
        List<GroupTaskResource> resources = groupTaskResourceRepository.getAllByGroupTask_Id(taskId);
        for(GroupTaskResource resource:resources)
        {
            responseData.add(map(resource));
        }
        return responseData;
    }

}

