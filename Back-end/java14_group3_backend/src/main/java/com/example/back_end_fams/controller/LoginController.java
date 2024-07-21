package com.example.back_end_fams.controller;

import com.example.back_end_fams.model.request.AuthenticationRequest;
import com.example.back_end_fams.model.request.UserRequest;
import com.example.back_end_fams.service.AuthenticationService;
import com.example.back_end_fams.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request, HttpServletRequest req){
        System.out.println("dang truy cap login trong loginController");
        return ResponseEntity.ok(authenticationService.authenticate(request, req));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(Authentication request){
        System.out.println(request.getAuthorities().toString());
        return ResponseEntity.ok(request);
    }
}
