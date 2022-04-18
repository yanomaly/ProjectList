package com.example.projectlist.repositories;

import com.example.projectlist.entites.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProblemsRepository extends JpaRepository<Problem, Long> {
    List<Problem> findAllByProjectID(Long project);

    @Transactional
    @Modifying
    @Query("UPDATE Problem SET isDelete = true WHERE projectID = :ID")
    void deleteProblem(@Param("ID") Long projectID);
}
