package com.example.platform.Task.GroupTask;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team,Long> {

    //Optional<Team> findTopByIdOrderByIdIdDesc();

}
