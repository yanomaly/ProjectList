package com.example.projectlist.controllers.UserControllers;

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
        setForms(model);
        return "edit_page";
    }

    @RequestMapping("/delete")
    @PostMapping
    public String deleteProject(@ModelAttribute("delete") Project projectForm, Model model){
        projectsRepository.deleteProject(projectForm.getProjectID());
        problemsRepository.deleteProblem(projectForm.getProjectID());
        setForms(model);
        return ("edit_page");
    }

    @RequestMapping("/project")
    @PostMapping
    public String editProject(@ModelAttribute("edit") DemoProject projectForm, Model model){
        String decision = projectService.validation(projectForm);
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        if(decision.equals("")) {
            Project newProject = projectService.createProject(projectForm);
            newProject.setProjectID(projectService.getProject_id(user_id));
            projectService.updateProject(newProject);
        }
        else
            model.addAttribute("decisionProjectEdit", decision);
        setForms(model);
        return ("edit_page");
    }

    @RequestMapping("/delete/problem")
    @PostMapping
    public String deleteProblem(@ModelAttribute("deleteProblem") Problem problem, Model model){
        problemsRepository.deleteProblemByProblemID(problem.getProblemID());
        setForms(model);
        return ("edit_page");
    }

    @RequestMapping("/problem")
    @PostMapping
    public String editProblem(@ModelAttribute("editProblem") Problem problem, Model model){
        String decision = problemService.validation(problem);
        if(decision.equals(""))
            problemsRepository.editProblem(problem.getProblemID(), problem.getName(), problem.getDescription());
        else
            model.addAttribute("decisionProblEdit", decision);
        setForms(model);
        return ("edit_page");
    }

    @RequestMapping("/add/problem")
    @PostMapping
    public String addProblem(@ModelAttribute("addProblem") Problem problem, Model model) {
        String decision = problemService.validation(problem);
        if (decision.equals(""))
            problemService.saveProblem(problem);
        else
            model.addAttribute("decisionProblAdd", decision);
        setForms(model);
        return ("edit_page");
    }

    public Model setForms(Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        long user_id = userRepository.findByUsername(loggedInUser.getName()).getUserID();
        model.addAttribute("project", projectsRepository.findByProjectIDAndIsDelete(projectService.getProject_id(user_id), false));
        model.addAttribute("problems", problemsRepository.findAllByProjectIDAndIsDelete(projectService.getProject_id(user_id), false));
        model.addAttribute("edit", new DemoProject());
        model.addAttribute("delete", new Project());
        model.addAttribute("editProblem", new Problem());
        model.addAttribute("deleteProblem", new Problem());
        model.addAttribute("addProblem", new Problem());
        return model;
    }
}

