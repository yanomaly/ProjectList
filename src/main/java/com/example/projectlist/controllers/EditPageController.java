package com.example.projectlist.controllers;

import com.example.projectlist.auxiliary.Button;
import com.example.projectlist.auxiliary.DemoProject;
import com.example.projectlist.entites.Problem;
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
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/edit")
public class EditPageController {

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
    public String editPage(Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        model.addAttribute("project", projectsRepository.findByProjectIDAndIsDelete(projectService.getProject_id(user_id), false));
        model.addAttribute("problems", problemsRepository.findAllByProjectIDAndIsDelete(projectService.getProject_id(user_id), false));
        model.addAttribute("edit", new DemoProject());
        model.addAttribute("delete", new Project());
        model.addAttribute("editProblem", new Problem());
        model.addAttribute("deleteProblem", new Problem());
        model.addAttribute("addProblem", new Problem());
        return "edit_page";
    }

    @RequestMapping("/delete")
    @PostMapping
    public String deleteProject(Model model){
        return("redirect:/edit");
    }

    @RequestMapping("/project")
    @PostMapping
    public String editProject(Model model){
        model.addAttribute("decisionProjectEdit", "decision");
        return("redirect:/edit");
    }

    @RequestMapping("/delete/problem")
    @PostMapping
    public String deleteProblem(Model model){
        return("redirect:/edit");
    }

    @RequestMapping("/problem")
    @PostMapping
    public String editProblem(Model model){
        model.addAttribute("decisionProblEdit", "decision");
        return("redirect:/edit");
    }

    @RequestMapping("/add/problem")
    @PostMapping
    public String addProblem(Model model){
        model.addAttribute("decisionProblAdd", "decision");
        return("redirect:/edit");
    }
}
