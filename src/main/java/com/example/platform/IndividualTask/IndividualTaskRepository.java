package com.example.platform.IndividualTask;

import com.example.platform.course.PendingInvite;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IndividualTaskRepository extends CrudRepository<IndividualTask,Long> {
    List<IndividualTask> getAllByCourse_Id(Long id);
    Optional<IndividualTask> getById(Long id);
}
