package com.example.projectlist.controllers.UserControllers;

import com.example.projectlist.entites.Problem;
import com.example.projectlist.repositories.UserRepository;
import com.example.projectlist.services.ProblemService;
import com.example.projectlist.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("count", problemService.getCount(projectID()));   //count of problems
        return "add_problems_page";
    }

    @PostMapping
    public String newProblem(@ModelAttribute("problemForm") Problem problemForm, Model model){
        String decision = problemService.validation(problemForm);
        if (decision.equals("")) {
            problemService.saveProblem(problemForm);
            model.addAttribute("count", problemService.getCount(projectID()));
            return "add_problems_page";
        } else {
            model.addAttribute("decision", decision);
            model.addAttribute("count", problemService.getCount(projectID())); //update count
            return "add_problems_page";
        }
    }

    public long projectID(){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        return projectService.getProject_id(userRepository.findByUsername(loggedInUser.getName()).getUserID());
    }
}
