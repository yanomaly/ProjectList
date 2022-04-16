package com.example.projectlist.controllers;

import com.example.projectlist.entites.Project;
import com.example.projectlist.repositories.UserRepository;
import com.example.projectlist.services.ProblemService;
import com.example.projectlist.services.ProjectService;
import com.example.projectlist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomePageController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProblemService problemService;

    @GetMapping
    public String homePage(Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUser_id();
        long project_id = projectService.getProject_id(user_id);
        if(project_id >= 0) {                                                            //delete from user_project & project_problem
            projectService.deleteFromUser_project(user_id);
            problemService.deleteProject(project_id);
        }
        model.addAttribute("projectForm", new Project());
        model.addAttribute("projects", userService.getData(user_id));
        return "home_page";
    }

    @RequestMapping("/filter")
    @PostMapping
    public String addList(@ModelAttribute("projectForm") Project projectForm, Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUser_id();
        userService.setData(user_id, projectService.filter(user_id, projectForm));
        return "home_page";
    }

    @RequestMapping("/delete")
    @PostMapping
    public String deleteProject(@ModelAttribute("projectForm") Project projectForm, Model model){

        return "home_page";
    }

    @RequestMapping("/view")
    @PostMapping
    public String detailView(@ModelAttribute("projectForm") Project projectForm, Model model){

        return "home_page";
    }

    @RequestMapping("/order")
    @PostMapping
    public String sortProjects(@ModelAttribute("projectForm") Project projectForm, Model model){

        return "home_page";
    }

    @RequestMapping("/edit")
    @PostMapping
    public String editProject(@ModelAttribute("projectForm") Project projectForm, Model model){

        return "home_page";
    }

    @RequestMapping("/next")
    @PostMapping
    public String next(@ModelAttribute("projectForm") Project projectForm, Model model){

        return "home_page";
    }

    @RequestMapping("/prev")
    @PostMapping
    public String previous(@ModelAttribute("projectForm") Project projectForm, Model model){

        return "home_page";
    }
}