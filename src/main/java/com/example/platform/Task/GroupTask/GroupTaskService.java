package com.example.platform.Task.GroupTask;

import com.example.platform.User.User;
import com.example.platform.User.UserRepository;
import com.example.platform.course.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        );
        }
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

    }

