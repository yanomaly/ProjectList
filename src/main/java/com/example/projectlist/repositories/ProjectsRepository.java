package com.example.projectlist.repositories;

import com.example.projectlist.entites.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProjectsRepository extends JpaRepository<Project, Long> {
    Page<Project> findAllByUserIDAndIsDelete(Long user_id, boolean isDelete, Pageable page);
    Project findByProjectIDAndIsDelete(Long projectID, boolean isDelete);


    @Transactional
    @Modifying
    @Query("UPDATE Project SET isDelete = true WHERE projectID = :ID")
    void deleteProject(@Param("ID") Long projectID);


}
