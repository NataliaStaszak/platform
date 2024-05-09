package com.example.platform.resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface TaskResourceRepository extends JpaRepository<TaskResource, String> {
}
