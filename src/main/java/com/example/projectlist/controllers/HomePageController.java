package com.example.projectlist.controllers;

import com.example.projectlist.auxiliary.Page;
import com.example.projectlist.entites.Project;
import com.example.projectlist.repositories.ProjectsRepository;
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

    @Autowired
    ProjectsRepository projectsRepository;

    @GetMapping
    public String homePage(Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        model.addAttribute("projectForm", new Project());
        model.addAttribute("projects", userService.getData(user_id));
        model.addAttribute("page", new Page());
        return "home_page";
    }

    @RequestMapping("/new")
    @GetMapping
    public String homePageNew(Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        long project_id = projectService.getProject_id(user_id);
        if(project_id >= 0) {                                                            //delete from user_project & project_problem
            projectService.deleteFromUser_project(user_id);
            problemService.deleteProject(project_id);
        }
        userService.setPage(user_id, 0);
        userService.setData(user_id, projectsRepository.findAllByUserID(user_id));
        return "redirect:/home";
    }

    @RequestMapping("/filter")
    @GetMapping
    public String addList(@ModelAttribute("projectForm") Project projectForm, Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        userService.setData(user_id, projectService.filter(user_id, projectForm));
        userService.setPage(user_id, 0);
        return "redirect:/home";
    }

    @RequestMapping("/delete")
    @PostMapping
    public String deleteProject(@ModelAttribute("projectForm") Project projectForm, Model model){

        return "redirect:/home";
    }

    @RequestMapping("/view")
    @GetMapping
    public String detailView(@ModelAttribute("projectForm") Project projectForm, Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        projectService.setActiveProject(user_id, projectForm.getProjectID());
        return "redirect:/view";
    }

    @RequestMapping("/edit")
    @PostMapping
    public String editProject(@ModelAttribute("projectForm") Project projectForm, Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        projectService.setActiveProject(user_id, projectForm.getProjectID());
        return "redirect:/edit";
    }

    @RequestMapping("/order")
    @GetMapping
    public String sortProjects(@ModelAttribute("projectForm") Project projectForm, Model model){

        return "redirect:/home";
    }

    @RequestMapping("/prev")
    @GetMapping
    public String previous(@ModelAttribute("page") Page page, Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        int set_page = userService.getPage(user_id) - 2 >= 0 ? userService.getPage(user_id) - 2 : 0;
        userService.setPage(user_id, set_page);
        return "redirect:/home";
    }

    @RequestMapping("/next")
    @GetMapping
    public String next(@ModelAttribute("page") Page page, Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        int set_page = userService.getPage(user_id) + 2 <= userService.getData(user_id).size() ? userService.getPage(user_id) + 2 : userService.getData(user_id).size();
        userService.setPage(user_id, set_page);
        return "redirect:/home";
    }
}