package com.example.projectlist.controllers;

import com.example.projectlist.entites.Project;
import com.example.projectlist.repositories.ProjectsRepository;
import com.example.projectlist.repositories.UserRepository;
import com.example.projectlist.services.ProjectService;
import com.example.projectlist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/add")
public class AddPageController {

    @Autowired
    ProjectsRepository projectsRepository;
    @Autowired
    ProjectService projectService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @GetMapping
    public String addPage(Model model){
        model.addAttribute("projectForm", new Project());
        return "add_page";
    }

    @PostMapping
    public String newProject(@ModelAttribute("projectForm") Project projectForm, Model model){
        String decision = projectService.validation(projectForm);
        if(decision.equals("")){
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            projectForm.setUserID(userRepository.findByUsername(loggedInUser.getName()).getUserID());
            projectService.saveProject(projectForm);
            return "redirect:/problems";
        }
        else{
            model.addAttribute("decision", decision);
            return "add_page";
        }
    }
}
