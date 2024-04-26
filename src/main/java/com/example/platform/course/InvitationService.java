package com.example.platform.course;

import com.example.platform.User.User;
import com.example.platform.User.UserDTO;
import com.example.platform.User.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvitationService {
    private final PendingInviteRepository repository;
    private final UserService userService;
    private final CourseService courseService;

    public InvitationService(PendingInviteRepository repository, UserService userService, CourseService courseService) {
        this.repository = repository;
        this.userService = userService;
        this.courseService = courseService;
    }
    public void askToJoin(User user, Course course) {
        repository.save(new PendingInvite(user,course));
    }
    public void acceptInvite(Long id) {
        Optional<PendingInvite> invite = repository.findById(id);
        invite.ifPresent(invitation ->
                invitation.getCourse().addAttendant(invitation.getUser())
                );
        repository.deleteById(id);

    }

    public void declineInvite(Long id) {
        repository.deleteById(id);
    }

    public List<InviteDTO> findCoursesUser(Long id) {
        List<InviteDTO> invites= new ArrayList<>();
        for(PendingInvite invite :repository.getAllByUser_Id(id))
        {
            invites.add(InvitationService.map(invite));
        }
        return invites;

    }

    public List<InviteDTO> findCoursesTeacher(Long id) {
        List<InviteDTO> invites= new ArrayList<>();
        for(PendingInvite invite :repository.getAllByCourse_Author_Id(id))
        {
            invites.add(InvitationService.map(invite));
        }
        return invites;

    }
    static InviteDTO map(PendingInvite invite){
        InviteDTO inviteDTO = new InviteDTO();
        inviteDTO.setId(invite.getId());
        inviteDTO.setUser(new UserDTO(invite.getUser().getId(),
                invite.getUser().getFirstName(),
                invite.getUser().getLastName(),
                invite.getUser().getEmail()));
        UserDTO author = new UserDTO(invite.getCourse().getAuthor().getId(),
                invite.getCourse().getAuthor().getFirstName(),
                invite.getCourse().getAuthor().getLastName(),
                invite.getCourse().getAuthor().getEmail());
        inviteDTO.setCourse(new CourseDTO(invite.getCourse().getId(),invite.getCourse().getName(),author));


        return inviteDTO;
    }
}
