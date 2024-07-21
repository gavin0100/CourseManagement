package com.example.back_end_fams.controller;

import ch.qos.logback.core.model.Model;
import com.example.back_end_fams.model.entity.User;
import com.example.back_end_fams.model.mapper.UserMapper;
import com.example.back_end_fams.model.request.UserRequest;
import com.example.back_end_fams.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/users")
//@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'CLASS_ADMIN')")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
//    @PreAuthorize("hasRole('SUPER_ADMIN')")
//    @PreAuthorize("hasRole('TRAINER')")
//    @PreAuthorize("hasAnyAuthority('FULL_ACCESS_CLASS_ROOM')")
//    @PreAuthorize("hasAnyAuthority('CREATE_SYLLYBUS', 'FULL_ACCESS_SYLLABUS')")
    @PreAuthorize("hasRole('SUPER_ADMIN') and hasAuthority('FULL_ACCESS_CLASS_ROOM')")
    public ResponseEntity<?> getAllUser(){
        List<User> userList = userService.findAll();
        return ResponseEntity.ok(userMapper.toUserListDTO(userList));
    }
    @PostMapping("/viewUserEdit")
    public String editUser(@RequestParam("userPermissionId") int userPermissionId,
                           @RequestParam("name") String name,
                           @RequestParam("email") String email,
                           @RequestParam("phone") String phone,
                           @RequestParam("dob") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dob,
                           @RequestParam("gender") String gender,
                           @RequestParam("status") Boolean status,
                           @RequestParam("userId") int userId,
                           Model model){

        System.out.println("Update user" + userPermissionId);
        System.out.println("Update user" + name);
        System.out.println("Update user" + email);
        System.out.println("Update user" + phone);
        System.out.println("Update user" + dob);
        System.out.println("Update user" + gender);
        System.out.println("Update user" + status);
        System.out.println("Update user" + userId);


        return "redirect:/api/users";

    }
    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword(@RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.updatePassword(userRequest));
    }

}
