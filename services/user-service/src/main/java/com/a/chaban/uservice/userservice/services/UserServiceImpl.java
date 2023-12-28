package com.a.chaban.uservice.userservice.services;

import com.a.chaban.uservice.userservice.models.User;
import com.a.chaban.uservice.userservice.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User findUserById(Long id) {
        return userRepo.findUserById(id);
    }
}
