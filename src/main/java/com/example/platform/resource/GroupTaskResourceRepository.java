package com.example.platform.resource;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupTaskResourceRepository extends CrudRepository<GroupTaskResource,String> {
}
