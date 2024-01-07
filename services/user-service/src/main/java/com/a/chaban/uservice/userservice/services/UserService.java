package com.a.chaban.uservice.userservice.services;

import com.a.chaban.uservice.userservice.models.User;

public interface UserService {

        User findByUsername(String username);

        User findUserById(Long id);
}
