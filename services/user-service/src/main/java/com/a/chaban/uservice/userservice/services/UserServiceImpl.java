package com.a.chaban.uservice.userservice.services;

import com.a.chaban.uservice.userservice.models.Role;
import com.a.chaban.uservice.userservice.models.User;
import com.a.chaban.uservice.userservice.repositories.UserRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.naming.NameAlreadyBoundException;
import java.util.List;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User findUserById(Long id) {
        return userRepo.findUserById(id);
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User createUser(User user) throws NameAlreadyBoundException {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new NameAlreadyBoundException("Username already taken");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_EDITOR);
        return userRepo.save(user);
    }
}
