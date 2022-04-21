package com.example.projectlist.controllers.UserControllers;

import com.example.projectlist.auxiliary.DemoProject;
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
    public String newProject(@ModelAttribute("projectForm") DemoProject projectForm, Model model){
        String decision = projectService.validation(projectForm);
        if(decision.equals("")){
            Project project = projectService.createProject(projectForm);
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            project.setUserID(userRepository.findByUsername(loggedInUser.getName()).getUserID());
            projectService.saveProject(project);
            return "redirect:/problems";
        }
        else{
            model.addAttribute("decision", decision);
            return "add_page";
        }
    }
}
