package com.example.platform.resource;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.MediaType;
import org.springframework.core.io.Resource;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/resources")

public class ResourceController {

    private TaskResourceService taskResourceService;
    private GroupTaskResourceService groupTaskResourceService;

    public ResourceController(TaskResourceService taskResourceService, GroupTaskResourceService groupTaskResourceService) {
        this.taskResourceService = taskResourceService;
        this.groupTaskResourceService = groupTaskResourceService;
    }

    //Individual
    @PostMapping("/upload/{id}")
    public ResponseData uploadFile(@RequestParam("file")MultipartFile file,@PathVariable Long id, Principal connectedUser) throws Exception {
        TaskResource taskResource = null;
        String downloadURl = "";
        taskResource = taskResourceService.saveTaskResource(file,id,connectedUser);
        downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/resources/download/")
                .path(taskResource.getId())
                .toUriString();

        return new ResponseData(taskResource.getFileName(),
                downloadURl,
                file.getContentType(),
                file.getSize());
    }
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteResource(@PathVariable String id) {
        taskResourceService.delete(id);
        return ResponseEntity.noContent().build();}

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
        TaskResource taskResource = null;
        taskResource = taskResourceService.getAttachment(fileId);
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(taskResource.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + taskResource.getFileName()
                                + "\"")
                .body(new ByteArrayResource(taskResource.getData()));
    }

    //GET ALL RESOURCES FROM TASK AS ADMIN/USER
    @GetMapping("/myFromTask/{id}")
    public List<TaskResourceDTO> getUserResourcesfromTask(@PathVariable Long id, Principal connectedUser) throws Exception {
        return taskResourceService.getResourcesFromTaskOfUser(id,connectedUser);
    }
    @GetMapping("/fromTask/{id}")
    public List<TaskResourceDTO> getResourcesfromTask(@PathVariable Long id) throws Exception {
        return taskResourceService.getResourcesFromTask(id);
    }
    //Group

    @PostMapping("/uploadGroup/{id}")
    public ResponseData uploadGroupFile(@RequestParam("file")MultipartFile file,@PathVariable Long id, Principal connectedUser) throws Exception {
        GroupTaskResource grouptaskResource = null;
        String downloadURl = "";
        grouptaskResource = groupTaskResourceService.saveGroupTaskResource(file,id,connectedUser);
        downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/resources/downloadGroup/")
                .path(grouptaskResource.getId())
                .toUriString();

        return new ResponseData(grouptaskResource.getFileName(),
                downloadURl,
                file.getContentType(),
                file.getSize());
    }
    @DeleteMapping("/group/{id}")
    ResponseEntity<?> deleteGroupResource(@PathVariable String id) {
        groupTaskResourceService.delete(id);
        return ResponseEntity.noContent().build();}

    @GetMapping("/downloadGroup/{fileId}")
    public ResponseEntity<Resource> downloadGroupFile(@PathVariable String fileId) throws Exception {
        GroupTaskResource taskResource = null;
        taskResource = groupTaskResourceService.getAttachment(fileId);
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(taskResource.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + taskResource.getFileName()
                                + "\"")
                .body(new ByteArrayResource(taskResource.getData()));
    }
    //GET ALL RESOURCES FROM TASK AS ADMIN/USER
    @GetMapping("/myFromGroupTask/{id}")
    public List<TaskResourceDTO> getUserResourcesfromGroupTask(@PathVariable Long id, Principal connectedUser) throws Exception {
        return groupTaskResourceService.getResourcesFromGroupTaskOfUser(id,connectedUser);
    }
    @GetMapping("/fromGroupTask/{id}")
    public List<TaskResourceDTO> getResourcesfromGroupTask(@PathVariable Long id) throws Exception {
        return groupTaskResourceService.getResourcesFromGroupTask(id);
    }

}