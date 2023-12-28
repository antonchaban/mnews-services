package com.a.chaban.uservice.userservice.services;

import com.a.chaban.uservice.userservice.models.User;
import org.springframework.stereotype.Service;


@Service
public interface UserService {

        User findByUsername(String username);

        User findUserById(Long id);
}
