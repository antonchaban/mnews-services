package com.a.chaban.uservice.userservice.services;

import com.a.chaban.uservice.userservice.dtos.UserDto;
import com.a.chaban.uservice.userservice.models.User;

public interface UserService {

        User findByUsername(String username);

        UserDto findUserById(Long id);
}
