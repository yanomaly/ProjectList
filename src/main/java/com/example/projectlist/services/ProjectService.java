package com.example.projectlist.services;

import com.example.projectlist.entites.Project;
import com.example.projectlist.repositories.ProjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProjectService {

    @Autowired
    ProjectsRepository projectsRepository;

    List<Project> projects = projectsRepository.findAll();


}
