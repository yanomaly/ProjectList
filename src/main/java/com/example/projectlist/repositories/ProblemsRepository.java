package com.example.projectlist.repositories;

import com.example.projectlist.entites.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemsRepository extends JpaRepository<Problem, Long> {
    List<Problem> findAllByProjectID(Long project);
}
