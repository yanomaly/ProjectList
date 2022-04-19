package com.example.projectlist.controllers;

import com.example.projectlist.repositories.ProblemsRepository;
import com.example.projectlist.repositories.ProjectsRepository;
import com.example.projectlist.repositories.UserRepository;
import com.example.projectlist.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/view")
public class DetailViewPageController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    ProblemsRepository problemsRepository;

    @GetMapping
    public String detailViewPage(Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        model.addAttribute("project", projectsRepository.findByProjectIDAndIsDelete(projectService.getProject_id(user_id), false));
        model.addAttribute("problems", problemsRepository.findAllByProjectIDAndIsDelete(projectService.getProject_id(user_id), false));
        return "detail_view_page";
    }

}