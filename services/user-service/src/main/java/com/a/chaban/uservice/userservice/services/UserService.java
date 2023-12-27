package com.a.chaban.uservice.userservice.services;

import com.a.chaban.uservice.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserService extends JpaRepository<User, Long> {

        User findByUsername(String username);

        User findUserById(Long id);
}
