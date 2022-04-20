package com.example.projectlist.repositories;

import com.example.projectlist.entites.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProblemsRepository extends JpaRepository<Problem, Long> {
    List<Problem> findAllByProjectIDAndIsDelete(Long project, boolean isDelete);

    @Transactional
    @Modifying
    @Query("UPDATE Problem SET isDelete = true WHERE projectID = :ID")
    void deleteProblem(@Param("ID") Long projectID);

    @Transactional
    @Modifying
    @Query("UPDATE Problem SET isDelete = true WHERE problemID = :ID")
    void deleteProblemByProblemID(@Param("ID") Long problemID);

    @Transactional
    @Modifying
    @Query("UPDATE Problem SET name = :name, description = :description WHERE problemID = :ID")
    void editProblem(@Param("ID") Long problemID,
                     @Param("name") String name,
                     @Param("description") String description);
}
