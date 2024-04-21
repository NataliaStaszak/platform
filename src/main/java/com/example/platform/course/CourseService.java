package com.example.platform.course;
import com.example.platform.User.Role;
import com.example.platform.User.User;
import com.example.platform.User.UserDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository repository;

    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }
    public List<Course> findAllCourses() {
        /*List<UserDTO> users= new ArrayList<>();
        for (User user : repository.findAllUsersByRole(Role.USER))
            users.add(new UserDTO(user.getId(),user.getFirstName(), user.getLastName(), user.getEmail()));*/
        return (List<Course>) repository.findAll();
    }
    Optional<CourseDTO> getCourseById(Long id){
        return repository.findById(id).map(CourseService::map);


    }

    static CourseDTO map(Course course){
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setAuthor(new UserDTO(course.getAuthor().getId(),
                course.getAuthor().getFirstName(),
                course.getAuthor().getLastName(),
                course.getAuthor().getEmail()
        ));

        return courseDTO;
    }
}
