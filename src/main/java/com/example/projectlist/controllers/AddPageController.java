package com.example.projectlist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/add")
public class AddPageController {

    @GetMapping
    public String mainPage(Model model){
        return "add_page";
    }

}
