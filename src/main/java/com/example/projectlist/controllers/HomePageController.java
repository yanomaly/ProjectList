package com.example.projectlist.controllers;

import com.example.projectlist.auxiliary.Page;
import com.example.projectlist.auxiliary.SortType;
import com.example.projectlist.entites.Project;
import com.example.projectlist.repositories.ProblemsRepository;
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

    @Autowired
    ProblemsRepository problemsRepository;

    @GetMapping
    public String homePage(Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        model.addAttribute("projectForm", new Project());
        model.addAttribute("order", new Project());
        model.addAttribute("view", new Project());
        model.addAttribute("edit", new Project());
        model.addAttribute("delete", new Project());
        model.addAttribute("projects", projectService.getData(user_id));
        model.addAttribute("page", new Page());
        model.addAttribute("sort", new SortType());
        return "home_page";
    }

    @RequestMapping("/new")
    @GetMapping
    public String homePageNew(Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        userService.setPage(user_id, 0);
        userService.setRequest(user_id, null);
        return "redirect:/home";
    }

    @RequestMapping("/filter")
    @GetMapping
    public String addList(@ModelAttribute("projectForm") Project projectForm, Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        userService.setRequest(user_id, projectForm);
        userService.setPage(user_id, 0);
        return "redirect:/home";
    }

    @RequestMapping("/delete")
    @PostMapping
    public String deleteProject(@ModelAttribute("delete") Project projectForm, Model model){
        projectsRepository.deleteProject(projectForm.getProjectID());
        problemsRepository.deleteProblem(projectForm.getProjectID());
        return "redirect:/home/new";
    }

    @RequestMapping("/view")
    @GetMapping
    public String detailView(@ModelAttribute("view") Project projectForm, Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        projectService.setActiveProject(user_id, projectForm.getProjectID());
        return "redirect:/view";
    }

    @RequestMapping("/edit")
    @PostMapping
    public String editProject(@ModelAttribute("edit") Project projectForm, Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        projectService.setActiveProject(user_id, projectForm.getProjectID());
        return "redirect:/edit";
    }

    @RequestMapping("/order")
    @GetMapping
    public String sortProjects(@ModelAttribute("sort") SortType sortType, Model model){

        return "redirect:/home";
    }

    @RequestMapping("/prev")
    @GetMapping
    public String previous(@ModelAttribute("page") Page page, Model model){
        long user_id = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserID();
        int prevPage = userService.getPage(user_id) - 1 >= 0 ? userService.getPage(user_id) - 1 : 0;
        userService.setPage(user_id, prevPage);
        return "redirect:/home";
    }

    @RequestMapping("/next")
    @GetMapping
    public String next(@ModelAttribute("page") Page page, Model model){
        long user_id = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserID();
        int nextPage = userService.getPage(user_id) + 1 <= userService.getMaxPage(user_id) ? userService.getPage(user_id) + 1 : userService.getMaxPage(user_id);
        userService.setPage(user_id, nextPage);
        return "redirect:/home";
    }
}