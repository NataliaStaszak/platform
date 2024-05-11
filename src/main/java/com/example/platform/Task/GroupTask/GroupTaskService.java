package com.example.platform.Task.GroupTask;

import com.example.platform.Task.DeadlineChangeRequest;
import com.example.platform.Task.IndividualTask.IndividualTask;
import com.example.platform.Task.IndividualTask.IndividualTaskDTO;
import com.example.platform.Task.IndividualTask.IndividualTaskService;
import com.example.platform.User.User;
import com.example.platform.User.UserDTO;
import com.example.platform.User.UserRepository;
import com.example.platform.course.Course;
import com.example.platform.course.CourseDTO;
import com.example.platform.course.CourseRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GroupTaskService {
    private final GroupTaskRepository groupTaskRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public GroupTaskService(GroupTaskRepository groupTaskRepository, TeamRepository teamRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.groupTaskRepository = groupTaskRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }
    public Optional<GroupTaskDTO> getGroupTaskById(Long id) {
        return groupTaskRepository.findById(id).map(GroupTaskService::map);

    }

    public List<GroupTaskDTO> findAllGroupTaskfromCourse(Long id) {
        List<GroupTaskDTO> tasks= new ArrayList<>();
        for (GroupTask task: groupTaskRepository.getAllByCourse_Id(id))
            tasks.add(GroupTaskService.map(task));
        return tasks;
    }

    public List<GroupTaskDTO> findAllOfUser(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        List<Course> courses = courseRepository.findAllByAttendantsContains(user);
        List<GroupTaskDTO> tasks= new ArrayList<>();
        for(Course course: courses){
            for(GroupTask task :groupTaskRepository.getAllByCourse_Id(course.getId())){
                tasks.add(GroupTaskService.map(task));
            }
        }
        return tasks;
    }


    public List<GroupTaskDTO> findAllOfAdmin(Principal connectedUser) {
        List<Course> courses = courseRepository.findAllByAuthorId(
                ((User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal()).getId()
        );
        List<GroupTaskDTO> tasks= new ArrayList<>();
        for(Course course: courses){
            for(GroupTask task :groupTaskRepository.getAllByCourse_Id(course.getId())){
                tasks.add(GroupTaskService.map(task));
            }
        }
        return tasks;
    }


    public Long createTeam(List<Long> ids){
        List<User> users = userRepository.findAllById(ids);
        return teamRepository.save(new Team(users)).getId();
    }


    public void createGroupTask(CreateGroupTaskRequest request) {
        List<Team> teams = new ArrayList<>();
        for (List<Long> team:request.getTeams()){
            teamRepository.findById(createTeam(team)).ifPresent(teams::add);

        }
        courseRepository.findById(request.getCourseId()).ifPresent( course->
                groupTaskRepository.save(
                        new GroupTask(course,new Date(),request.getDeadline(), request.getContents(),teams))
        );}

    public void deleteTask(Long id){
        List<Long> teamsIds= new ArrayList<>();
        groupTaskRepository.findById(id).ifPresent(task->{
            for(Team team:task.getTeams()){
                teamsIds.add(team.getId());
            }});
        groupTaskRepository.deleteById(id);
        for (Long teamid:teamsIds) {
            teamRepository.deleteById(teamid);
        }}


    public void deleteTeam(Long id){
        teamRepository.deleteById(id);
    }

    public void changeDeadline(DeadlineChangeRequest request)
    {
        groupTaskRepository.findById(request.getTaskId()).ifPresent(task -> {
            task.setDeadline(request.getNewDeadline());
            groupTaskRepository.save(task);
        });
    }
    public boolean isUserAssignedtoGroupTask(Principal connectedUser,Long id)
    {
        var searchedUser = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        List<Long> ids=new ArrayList<>();
        groupTaskRepository.findById(id).ifPresent(task->
        {
            for (Team team: task.getTeams()){
                for (User user: team.getMembers()){
                    if (user.getId()==searchedUser.getId())
                        ids.add(user.getId());
                }
            }
        });

        if(ids.isEmpty())
            return false;
        return true;

    }


    static GroupTaskDTO map(GroupTask groupTask){

        List<TeamDTO> teamsDTO=new ArrayList<>();
        for(Team team :groupTask.getTeams())
        {
            teamsDTO.add(map(team));
        }
        return new GroupTaskDTO(groupTask.getId(), groupTask.getCourse().getName()
                ,groupTask.getDate(),groupTask.getDeadline(), groupTask.getContents(), teamsDTO);

    }


    static TeamDTO map(Team team){
        List<UserDTO> users=new ArrayList<>();
        for(User user: team.getMembers())
            users.add(new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail()));
        return new TeamDTO(team.getId(), users);
    }



    }

