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

@RestController
@RequestMapping("/api/v1/resources")

public class ResourceController {

    private TaskResourceService taskResourceService;

    public ResourceController(TaskResourceService taskResourceService) {
        this.taskResourceService = taskResourceService;
    }

    @PostMapping("/upload")
    public ResponseData uploadFile(@RequestParam("file")MultipartFile file, Principal connectedUser) throws Exception {
        TaskResource taskResource = null;
        String downloadURl = "";
        taskResource = taskResourceService.saveTaskResource(file,connectedUser);
        downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/resources/download/")
                .path(taskResource.getId())
                .toUriString();

        return new ResponseData(taskResource.getFileName(),
                downloadURl,
                file.getContentType(),
                file.getSize());
    }

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
}