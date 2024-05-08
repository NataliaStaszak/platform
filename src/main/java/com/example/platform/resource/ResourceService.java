package com.example.platform.resource;

import java.util.Collections;
import java.util.List;

public class ResourceService {
    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public List<Resource> findAllResources() {
        List<Resource> resources = resourceRepository.findAll();
        return resources;
    }

    public List<Resource> getResourceById(Long id) {
        List<Resource> resources = resourceRepository.findAllById(Collections.singleton(id));
        return resources;
    }

    public void createResource(Resource resource) {
        if(!resourceRepository.existsById(resource.getId()))
        {
            resourceRepository.save(new Resource(resource.getIndividualTask(), resource.getAuthor(), resource.getDate()));
        }
    }

    public void deleteResource(Long id)
    {
        System.out.println("DELETED BY ID");
        resourceRepository.deleteById(id);
    }
}
