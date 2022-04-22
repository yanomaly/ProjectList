package com.example.projectlist.controllers.AdminControllers;

import com.example.projectlist.auxiliary.Button;
import com.example.projectlist.entites.Project;
import com.example.projectlist.repositories.ProjectsRepository;
import com.example.projectlist.repositories.UserRepository;
import com.example.projectlist.services.ProjectService;
import com.example.projectlist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/projects")
public class UserProjectsPageController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectsRepository projectsRepository;

    @GetMapping
    public String userProjects(Model model){
        userService.setPage(adminID(), 0);
        setForms(model);
        return "user_projects";
    }

    @RequestMapping("/view/redir")
    @GetMapping
    public String redirect(@ModelAttribute("view") Project project, Model model){
        projectService.setActiveProject(adminID(), project.getProjectID());
        return "redirect:/admin/view";
    }

    @RequestMapping("/prev")
    @GetMapping
    public String previous(@ModelAttribute("page") Button page, Model model){
        int prevPage = userService.getPage(adminID()) - 1 >= 0 ? userService.getPage(adminID()) - 1 : 0;
        userService.setPage(adminID(), prevPage);
        setForms(model);
        return "user_projects";
    }

    @RequestMapping("/next")
    @GetMapping
    public String next(@ModelAttribute("page") Button page, Model model){
        int nextPage = userService.getPage(adminID()) + 1 <= userService.getMaxPage(adminID()) ? userService.getPage(adminID()) + 1 : userService.getMaxPage(adminID());
        userService.setPage(adminID(), nextPage);
        setForms(model);
        return "user_projects";
    }

    public void setForms(Model model){
        Pageable page = PageRequest.of(userService.getPage(adminID()), 2);
        userService.setMaxPage(adminID(), page.getPageSize());
        model.addAttribute("projects", projectsRepository.findAllByUserIDAndIsDelete(userService.getUserID(adminID()), false, page).getContent());
        model.addAttribute("view", new Project());
        model.addAttribute("page", new Button());
    }
    public long adminID(){
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(loggedInUser.getName()).getUserID();
    }
}
