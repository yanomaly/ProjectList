package com.example.projectlist.controllers.AdminControllers;

import com.example.projectlist.entites.User;
import com.example.projectlist.repositories.ProblemsRepository;
import com.example.projectlist.repositories.ProjectsRepository;
import com.example.projectlist.repositories.UserRepository;
import com.example.projectlist.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/view")
public class UserProjectDetailViewController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    ProblemsRepository problemsRepository;

    @GetMapping
    public String detailViewPage(Model model){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        User admin = userRepository.findByUsername(loggedInUser.getName());
        long admin_id = admin.getUserID();
        model.addAttribute("project", projectsRepository.findByProjectIDAndIsDelete(projectService.getProject_id(admin_id), false));
        model.addAttribute("problems", problemsRepository.findAllByProjectIDAndIsDelete(projectService.getProject_id(admin_id), false));
        model.addAttribute("role", admin.getRoles().iterator().next().getId());
        return "detail_view_page";
    }
}
