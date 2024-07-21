package com.example.back_end_fams.controller;

import com.example.back_end_fams.config.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class Home {
    @Autowired
    private JwtFilter jwtFilter;
    @GetMapping
    public String login(Model model){

        if (jwtFilter.getAccessToken() == null){
            return "ui_login";
        }
        return "redirect:/home";
    }
}
