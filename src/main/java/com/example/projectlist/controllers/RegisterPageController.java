package com.example.projectlist.controllers;

import com.example.projectlist.entites.User;
import com.example.projectlist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.regex.Pattern;

@Controller
@RequestMapping("/register")
public class RegisterPageController {

    @Autowired
    UserService userService;

    @GetMapping
    public String registerPage(Model model){
        model.addAttribute("userForm", new User());
        return "register_page";
    }

    @PostMapping
    public String newUser(@ModelAttribute("userForm") User userForm, Model model){
        String decision = userService.validation(userForm);
        if(decision.equals("")) {
            userService.saveUser(userForm);
            return "redirect:/login";
        }
        else {
            model.addAttribute("decision", decision);
            return "register_page";
        }
    }
}
