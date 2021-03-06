package com.example.projectlist.controllers.UserControllers;

import com.example.projectlist.entites.User;
import com.example.projectlist.repositories.ProblemsRepository;
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

    @Autowired
    UserService userService;

    @GetMapping
    public String detailViewPage(Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(loggedInUser.getName());
        long user_id = user.getUserID();
        model.addAttribute("project", projectsRepository.findByProjectIDAndIsDelete(projectService.getProject_id(user_id), false));
        model.addAttribute("problems", problemsRepository.findAllByProjectIDAndIsDelete(projectService.getProject_id(user_id), false));
        model.addAttribute("role", user.getRoles().iterator().next().getId());
        return "detail_view_page";
    }

}