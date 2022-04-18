package com.example.projectlist.repositories;

import com.example.projectlist.entites.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectsRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByUserID(Long user_id);
    Project findByProjectID(Long projectID);

    @Transactional
    @Modifying
    @Query("UPDATE Project SET isDelete = true WHERE projectID = :ID")
    void deleteProject(@Param("ID") Long projectID);
}
