package com.example.projectlist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/view")
public class DetailViewPageController {

    @GetMapping
    public String mainPage(Model model){
        return "detal_view_page";
    }

}