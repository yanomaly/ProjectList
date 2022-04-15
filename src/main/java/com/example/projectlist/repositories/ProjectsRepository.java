package com.example.projectlist.repositories;

import com.example.projectlist.entites.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectsRepository extends JpaRepository<Project, Long> {

}
