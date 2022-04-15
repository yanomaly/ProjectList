package com.example.projectlist.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/edit")
public class EditPageController {

    @GetMapping
    public String editPage(Model model){
        return "edit_page";
    }

}
