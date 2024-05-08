package com.example.platform.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resources")

public class ResourceController {

    private ResourceService resourceService;

    @GetMapping()
    public ResponseEntity<List<Resource>> findAllResources() {
        return ResponseEntity.ok(resourceService.findAllResources());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<List<Resource>> GetById(@PathVariable Long id){
        return ResponseEntity.ok(resourceService.getResourceById(id));
    }

    @PostMapping("/createResource")
    public ResponseEntity<?> createResource(@RequestBody Resource resource){
        resourceService.createResource(resource);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    ResponseEntity<?> deleteResource(@RequestBody Resource resource){
        resourceService.deleteResource(resource.getId());
        return ResponseEntity.noContent().build();
    }
}