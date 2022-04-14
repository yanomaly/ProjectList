package com.example.projectlist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class MainPageController {

    @GetMapping
    public String mainPage(Model model){
        return "main_page";
    }

}