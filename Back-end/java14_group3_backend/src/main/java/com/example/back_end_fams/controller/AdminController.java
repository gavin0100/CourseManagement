package com.example.back_end_fams.controller;

import com.example.back_end_fams.model.entity.EmailDetails;
import com.example.back_end_fams.model.entity.User;
import com.example.back_end_fams.model.entity.UserPermission;
import com.example.back_end_fams.model.mapper.UserMapper;
import com.example.back_end_fams.model.mapper.UserPermissionMapper;
import com.example.back_end_fams.model.request.UserPermissionRequest;
import com.example.back_end_fams.model.request.UserRequest;
import com.example.back_end_fams.repository.EmailService;
import com.example.back_end_fams.repository.UserPermissionRepository;
import com.example.back_end_fams.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserPermissionMapper userPermissionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserPermissionRepository userPermissionRepository;

    @GetMapping("/user")
    public ResponseEntity<?> getAllUser(){
        List<User> userList = userService.findAll();
        return ResponseEntity.ok(userMapper.toUserListDTO(userList));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getById(@PathVariable int userId){
       User user = userService.getUserById(userId);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }
    @PostMapping("/user/create")
    public String createUser(@RequestBody UserRequest userRequest)
    {
        return userService.createUser(userRequest);
    }
    @PutMapping("/user/update/{userId}")
    public String updateUser(@PathVariable int userId,@RequestBody UserRequest userRequest)
    {
        return userService.updateUser(userId,userRequest);
    }
    @PutMapping("/user/status/{userId}")
    public String setStatusUser(@PathVariable int userId)
    {
        return userService.setStatusUser(userId);
    }
    @DeleteMapping("/user/delete/{userId}")
    public String deleteUser(@PathVariable int userId)
    {
        return userService.deleteUser(userId);
    }
    @PutMapping("/user/update-permission/{userId}")
    public String updatePermissiontoUser(@PathVariable int userId,@RequestBody UserRequest userRequest)
    {
        return userService.updatePermissonToUser(userId,userRequest);
    }
    @GetMapping("/user-permission")
    public ResponseEntity<?> getUserPermission(){
        List<UserPermission> userPermissions = userService.fillAllUserPermission();
        return ResponseEntity.ok(userPermissionMapper.toUserPermissionListDTO(userPermissions));
    }
    @PutMapping("/user-permission/update")
    public ResponseEntity<?> updateUserPermission(@RequestBody List<UserPermissionRequest> userPermissionRequest){
        List<UserPermission> userPermissions = userService.updateUserPermission(userPermissionMapper.toUserPermissionListEntity(userPermissionRequest));
        return ResponseEntity.ok(userPermissionMapper.toUserPermissionListDTO(userPermissions));
    }








}
