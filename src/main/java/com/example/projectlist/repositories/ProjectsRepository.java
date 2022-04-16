package com.example.projectlist.repositories;

import com.example.projectlist.entites.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectsRepository extends JpaRepository<Project, Long> {
    public List<Project> findByUser_id(long user_id);
}
