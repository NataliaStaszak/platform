package com.example.platform.resource;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface GroupTaskResourceRepository extends CrudRepository<GroupTaskResource,String> {
    List<GroupTaskResource> getAllByAuthor_IdAndGroupTask_Id(Long AuthorId, Long TaskId);
    List<GroupTaskResource> getAllByGroupTask_Id(Long TaskId);
}