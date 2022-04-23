package com.example.projectlist.controllers.AdminControllers;

import com.example.projectlist.auxiliary.Button;
import com.example.projectlist.entites.User;
import com.example.projectlist.repositories.UserRepository;
import com.example.projectlist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminMainPageController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping
    public String users(Model model){
        userService.setPage(adminID(), 0);
        setForms(model);
        return "admin_main";
    }

    //button 'previous' clicked
    @RequestMapping("/prev")
    @GetMapping
    public String previous(@ModelAttribute("page") Button page, Model model){
        int prevPage = userService.getPage(adminID()) - 1 >= 0 ? userService.getPage(adminID()) - 1 : 0;
        userService.setPage(adminID(), prevPage);
        setForms(model);
        return "admin_main";
    }

    //button 'next' clicked
    @RequestMapping("/next")
    @GetMapping
    public String next(@ModelAttribute("page") Button page, Model model){
        int nextPage = userService.getPage(adminID()) + 1 <= userService.getMaxPage(adminID()) ? userService.getPage(adminID()) + 1 : userService.getMaxPage(adminID());
        userService.setPage(adminID(), nextPage);
        setForms(model);
        return "admin_main";
    }

    @RequestMapping("/projects/redir")
    @GetMapping
    public String projects(@ModelAttribute("user") User user, Model model){
        userService.setUserID(adminID(), user.getUserID());
        return "redirect:/admin/projects";
    }

    public void setForms(Model model){
        model.addAttribute("users", userService.getUsers(adminID()));
        model.addAttribute("user", new User());
        model.addAttribute("page", new Button());
    }
    public long adminID(){
    Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findByUsername(loggedInUser.getName()).getUserID();
}
}
