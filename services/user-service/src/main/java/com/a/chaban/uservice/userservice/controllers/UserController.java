package com.a.chaban.uservice.userservice.controllers;


import com.a.chaban.uservice.userservice.dtos.CreateUserDto;
import com.a.chaban.uservice.userservice.dtos.UpdateUserDto;
import com.a.chaban.uservice.userservice.dtos.UserDto;
import com.a.chaban.uservice.userservice.models.Role;
import com.a.chaban.uservice.userservice.models.User;
import com.a.chaban.uservice.userservice.repositories.UserRepo;
import com.a.chaban.uservice.userservice.services.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameAlreadyBoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final UserRepo userRepo;


    @GetMapping("users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("users")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto user) throws NameAlreadyBoundException {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findUserById(id));
    }


    @PatchMapping("users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserDto user,
                                              @CookieValue(name = "USER_ID") Long userId) {
        var curUser = userRepo.findById(userId).orElse(null);
        assert curUser != null;
        if (curUser.getRoles().stream().anyMatch(role -> role.equals(Role.ROLE_ADMIN))) {
            return ResponseEntity.ok(userService.updateUser(id, user));

        }
        return ResponseEntity.status(403).build();
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id, @CookieValue(name = "USER_ID") Long userId) {
        var curUser = userRepo.findById(userId).orElse(null);
        assert curUser != null;
        if (curUser.getRoles().stream().anyMatch(role -> role.equals(Role.ROLE_ADMIN))) {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(403).build();
    }
}
