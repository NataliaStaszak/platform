package com.example.platform.Task.GroupTask;

import com.example.platform.Task.IndividualTask.IndividualTask;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GroupTaskRepository extends CrudRepository<GroupTask,Long> {
    List<GroupTask> getAllByCourse_Id(Long id);
    Optional<GroupTask> getById(Long id);

}
