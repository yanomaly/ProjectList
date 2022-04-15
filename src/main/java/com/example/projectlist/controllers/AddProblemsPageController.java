package com.example.projectlist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/problems")
public class AddProblemsPageController {

    @GetMapping
    public String addProblemsPage(Model model){
        return "add_problems_page";
    }

}
