package com.example.mobimarket.controller;


import com.example.mobimarket.dto.request.UserRequest;
import com.example.mobimarket.entity.User;
import com.example.mobimarket.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PutMapping("/updateUserProfile/{id}")
    public String updateUserProfile(@PathVariable Long id, @RequestBody UserRequest request) {
        return userService.updateProfileById(id, request);
    }

    @PutMapping("/like")
    public String like(@RequestParam Long id) {
        return userService.likeProduct(id);
    }

    @GetMapping("/allUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
