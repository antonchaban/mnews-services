package com.a.chaban.uservice.userservice.controllers;


import com.a.chaban.uservice.userservice.dtos.UserDto;
import com.a.chaban.uservice.userservice.models.User;
import com.a.chaban.uservice.userservice.services.UserServiceImpl;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameAlreadyBoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("users") //
    public ResponseEntity<User> createUser(@RequestBody JSONObject jsonReq) throws NameAlreadyBoundException {
        var user = new User();
        user.setUsername(jsonReq.getAsString("username"));
        user.setPassword(jsonReq.getAsString("password"));
        return ResponseEntity.ok(userService.createUser(user));
    }
}
