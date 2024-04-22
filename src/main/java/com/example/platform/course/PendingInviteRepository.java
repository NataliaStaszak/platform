package com.example.platform.course;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PendingInviteRepository extends CrudRepository<PendingInvite,Long> {
    List<PendingInvite> getAllByCourse_Author_Id(Long id);
    List<PendingInvite> getAllByUser_Id(Long id);
}
