package com.example.back_end_fams.controller;

import com.example.back_end_fams.config.JwtFilter;
import com.example.back_end_fams.model.response.AuthenticaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@ControllerAdvice
@RequestMapping({"/", "/home", "/class", "/syllabus", "/calendar", "/user", "training_program"})
public class GlobalController {
    @Autowired
    JwtFilter jwtFilter;

    @ModelAttribute("AuthenticateResponse")
    public AuthenticaResponse getCategories() {
        AuthenticaResponse authenticaResponse = jwtFilter.getAuthenticaResponse();
        return authenticaResponse;
    }
}
