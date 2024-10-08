package com.example.platform.Task.GroupTask;

import com.example.platform.Task.DeadlineChangeRequest;
import com.example.platform.Task.TaskDTO;
import com.example.platform.User.User;
import com.example.platform.User.UserDTO;
import com.example.platform.User.UserRepository;
import com.example.platform.course.Course;
import com.example.platform.course.CourseRepository;
import com.example.platform.course.CourseService;
import com.example.platform.resource.GroupTaskResourceService;
import com.example.platform.resource.TaskResourceDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupTaskService {
    private final GroupTaskRepository groupTaskRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseService courseService;
    private final GroupTaskResourceService groupTaskResourceService;

    public GroupTaskService(GroupTaskRepository groupTaskRepository, TeamRepository teamRepository, UserRepository userRepository, CourseRepository courseRepository, CourseService courseService, GroupTaskResourceService groupTaskResourceService) {
        this.groupTaskRepository = groupTaskRepository;
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
        this.groupTaskResourceService = groupTaskResourceService;
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

    public List<GroupTaskDTO> findAllOfUserFromCourse(Principal connectedUser,Long id) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        List<GroupTaskDTO> tasks= new ArrayList<>();
        for(GroupTask task :groupTaskRepository.getAllByCourse_Id(id)){
            if (isUserAssignedtoGroupTask(connectedUser,task.getId()))
                tasks.add(GroupTaskService.map(task));
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
    public List<GroupTaskNotSolvedReport> findAllUnsolvedTasksOfAdmin(Principal connectedUser){
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Date now =new Date();
        List<GroupTask> tasks = groupTaskRepository.getAllByDeadlineBeforeAndAndCourse_Author_Id(now,user.getId());
        List<GroupTaskNotSolvedReport> reports=new ArrayList<>();
        for(GroupTask task:tasks)
        {
            List<TeamDTO> teams=new ArrayList<>();
            for(Team team:getAllTeamsWithoutSolution(task))
                teams.add(map(team));

            reports.add(new GroupTaskNotSolvedReport(GroupTaskService.mapTask(task),teams));

        }
        return reports;
    }

    public List<Team> getAllTeamsWithoutSolution(GroupTask task){
        List<Long> teamsIds=new ArrayList<>();
        for(Team team:task.getTeams())
            teamsIds.add(team.getId());
        List<Long> resourcesAuthors=new ArrayList<>();
        for(TaskResourceDTO resource:groupTaskResourceService.getResourcesFromTask(task.getId()))
        {
            resourcesAuthors.add(resource.getAuthorId());
        }
        List<Long> missingTeamsIds = teamsIds.stream()
                .filter(teamId -> !resourcesAuthors.contains(teamId))
                .collect(Collectors.toList());
        return (List<Team>) teamRepository.findAllById(missingTeamsIds);

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
    static TaskDTO mapTask(GroupTask groupTask){
        return new TaskDTO(groupTask.getId(), groupTask.getCourse().getName()
                ,groupTask.getDate(),groupTask.getDeadline(), groupTask.getContents());

    }


    static TeamDTO map(Team team){
        List<UserDTO> users=new ArrayList<>();
        for(User user: team.getMembers())
            users.add(new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail()));
        return new TeamDTO(team.getId(), users);
    }



    }

