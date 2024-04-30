package com.example.platform.Task.IndividualTask;

import com.example.platform.Task.IndividualTask.IndividualTask;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IndividualTaskRepository extends CrudRepository<IndividualTask,Long> {
    List<IndividualTask> getAllByCourse_Id(Long id);
    Optional<IndividualTask> getById(Long id);
}
