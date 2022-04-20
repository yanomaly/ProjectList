package com.example.projectlist.repositories;

import com.example.projectlist.entites.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;

public interface ProjectsRepository extends JpaRepository<Project, Long> {
    Page<Project> findAllByUserIDAndIsDelete(Long user_id, boolean isDelete, Pageable page);
    Project findByProjectIDAndIsDelete(Long projectID, boolean isDelete);

    @Query("SELECT p FROM Project p WHERE p.name like :name and p.head like :head and p.budget >= :budget1 and p.budget <= :budget2 and p.dateFinish >= :dateFinish1 and p.dateFinish <= :dateFinish2 and p.isDelete = false")
    Page<Project> filter(@Param("name") String name,
                        @Param("head") String head,
                        @Param("budget1") Integer budget1,
                        @Param("budget2") Integer budget2,
                        @Param("dateFinish1") Calendar dateFinish1,
                        @Param("dateFinish2") Calendar dateFinish2,
                        Pageable page);

    @Transactional
    @Modifying
    @Query("UPDATE Project SET isDelete = true WHERE projectID = :ID")
    void deleteProject(@Param("ID") Long projectID);

    @Transactional
    @Modifying
    @Query("UPDATE Project SET name = :name, head = :head, dateFinish = :dateFinish, budget = :budget WHERE projectID = :ID")
    void editProject(@Param("ID") Long projectID,
                     @Param("name") String name,
                     @Param("head") String head,
                     @Param("dateFinish") Calendar dateFinish,
                     @Param("budget") Integer budget);
}
