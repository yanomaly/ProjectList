package com.example.projectlist.repositories;

import com.example.projectlist.entites.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemsRepository extends JpaRepository<Problem, Long> {
}
