package com.example.platform.course;

import com.example.platform.User.User;
import com.example.platform.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/invitations")
public class InvitationController {
    private CourseService courseService;
    private InvitationService invitationService;

    public InvitationController(CourseService courseService, InvitationService invitationService) {
        this.courseService = courseService;
        this.invitationService = invitationService;
    }
    @GetMapping("/myInvitations")
    public ResponseEntity<List<InviteDTO>> findmyRequests(Principal connectedUser) {
        return ResponseEntity.ok(invitationService.findCoursesUser(
                ((User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal()).getId()
        ));
    }
    @GetMapping("/myInvitationsAdmin")
    public ResponseEntity<List<InviteDTO>> findmyRequestsAdmin(Principal connectedUser) {
        return ResponseEntity.ok(invitationService.findCoursesTeacher(
                ((User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal()).getId()
        ));
    }

    @PostMapping("/join/{id}")
    @ResponseBody
    public ResponseEntity<CourseDTO> JoinRequest(@PathVariable Long id,Principal connectedUser){
        Optional<Course> courseToJoin= courseService.getCourseById(id);
        courseToJoin.ifPresent( course -> {
            invitationService.askToJoin((User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal()
                    ,course);
        });
        return ResponseEntity.ok().build();
    }
    @Transactional
    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> accept(
            @PathVariable Long id
    ) {
        invitationService.acceptInvite(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteInvitation(@PathVariable Long id) {

        invitationService.declineInvite(id);
        return ResponseEntity.noContent().build();}
}
