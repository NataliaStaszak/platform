package com.example.platform.course;
import com.example.platform.User.Role;
import com.example.platform.User.User;
import com.example.platform.User.UserDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository repository;
    private final PendingInviteRepository pendingInviteRepository;

    public CourseService(CourseRepository repository, PendingInviteRepository pendingInviteRepository) {
        this.repository = repository;
        this.pendingInviteRepository = pendingInviteRepository;
    }


    public List<CourseDTO> findAllCourses() {
        List<CourseDTO> courses= new ArrayList<>();
        for (Course course : repository.findAll())
            courses.add(CourseService.map(course));
        return courses;
    }
    Optional<CourseDTO> getCourseDTOById(Long id){
        return repository.findById(id).map(CourseService::map);
    }
    Optional<Course> getCourseById(Long id){
        return repository.findById(id);
    }
    public void createCourse(SaveCourseDTO request, Principal connectedUser){
        var author = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        repository.save(new Course(request.getName(), author));
    }

    public void deleteCourse(Long id) {
        repository.deleteById(id);
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


    public List<UserDTO> findAllParticipants(Long id) {
        List<UserDTO> courseUsers = new ArrayList<>();
        repository.findById(id).ifPresent(course->
        {
            for (User user: course.getAttendants()) {
                courseUsers.add(new UserDTO(user.getId(),user.getFirstName(), user.getLastName(), user.getEmail()));
            }
        });
        return courseUsers;
    }

    public List<CourseDTO> findAllUserCourses(Principal connectedUser){
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        List<CourseDTO> courses= new ArrayList<>();
        for (Course course : repository.findAllByAttendantsContains(user))
            courses.add(CourseService.map(course));
        return courses;

    }
    public List<CourseDTO> findAllAuthorCourses(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        List<CourseDTO> courses = new ArrayList<>();
        for (Course course : repository.findAllByAuthorId(user.getId()))
            courses.add(CourseService.map(course));
        return courses;
    }
}
