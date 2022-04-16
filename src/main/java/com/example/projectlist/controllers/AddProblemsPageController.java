package com.example.projectlist.controllers;

import com.example.projectlist.entites.Problem;
import com.example.projectlist.entites.Project;
import com.example.projectlist.repositories.ProblemsRepository;
import com.example.projectlist.repositories.UserRepository;
import com.example.projectlist.services.ProblemService;
import com.example.projectlist.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@Controller
@RequestMapping("/problems")
public class AddProblemsPageController {

    @Autowired
    ProblemService problemService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectService projectService;

    @GetMapping
    public String addProblemsPage(Model model){
        model.addAttribute("problemForm", new Problem());
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long project_id = projectService.getProject_id(userRepository.findByUsername(loggedInUser.getName()).getUser_id()); //count of problems
        model.addAttribute("count", problemService.getCount(project_id));
        return "add_problems_page";
    }

    @PostMapping
    public String newProblem(@ModelAttribute("problemForm") Problem problemForm, Model model){
        String decision = problemService.validation(problemForm);
        if (decision.equals("")) {
            problemService.saveProblem(problemForm);
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            long project_id = projectService.getProject_id(userRepository.findByUsername(loggedInUser.getName()).getUser_id());
            model.addAttribute("count", problemService.getCount(project_id));
            return "add_problems_page";
        } else {
            model.addAttribute("decision", decision);
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            long project_id = projectService.getProject_id(userRepository.findByUsername(loggedInUser.getName()).getUser_id()); //update count
            model.addAttribute("count", problemService.getCount(project_id));
            return "add_problems_page";
        }
    }
}
