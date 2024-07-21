package com.example.back_end_fams.controller;

import com.example.back_end_fams.config.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
    public class TestController {
    public static String errorMassage;
    @Autowired
    private JwtFilter jwtFilter;

    @GetMapping("/home")
    public String getHome(Model model){
        if (jwtFilter.getAccessToken() == null){
            return "redirect:/user/login";
        }
        if (errorMassage != null){
            System.out.println(errorMassage);
            model.addAttribute("errorMessage", errorMassage);
            errorMassage = null;
        }
        String name = jwtFilter.getAuthenticaResponse().getName();
        model.addAttribute("name", name);
        return "home";
    }
    @GetMapping("/user")
    public String getUser(Model model){
        return "ui_users";
    }
    @GetMapping("/user_permission")
    public String getUserPermission(Model model){
        return "ui_user_permission";
    }
}
