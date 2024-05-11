package com.example.platform.resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


public interface TaskResourceRepository extends CrudRepository<TaskResource, String> {
    List<TaskResource> getAllByAuthor_IdAndIndividualTask_Id(Long AuthorId,Long TaskId);
    List<TaskResource> getAllByIndividualTask_Id(Long TaskId);
}
