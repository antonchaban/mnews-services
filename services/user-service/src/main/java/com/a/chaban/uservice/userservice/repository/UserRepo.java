package com.a.chaban.uservice.userservice.repository;

import com.a.chaban.uservice.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findUserById(Long id);
}
