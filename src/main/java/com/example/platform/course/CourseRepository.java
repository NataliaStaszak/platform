package com.example.platform.course;

import com.example.platform.User.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CourseRepository extends CrudRepository<Course,Long> {
    List<Course> findAllByAttendantsContains(User user);
    List<Course> findAllByAuthorId(Long id);


}
