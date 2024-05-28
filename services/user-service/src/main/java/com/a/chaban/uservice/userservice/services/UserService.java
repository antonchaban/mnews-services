package com.a.chaban.uservice.userservice.services;

import com.a.chaban.uservice.userservice.dtos.CreateUserDto;
import com.a.chaban.uservice.userservice.dtos.UpdateUserDto;
import com.a.chaban.uservice.userservice.dtos.UserDto;
import com.a.chaban.uservice.userservice.models.User;

import javax.naming.NameAlreadyBoundException;
import java.util.List;

public interface UserService {

    User findByUsername(String username);

    UserDto findUserById(Long id);

    List<UserDto> findAll();

    User createUser(CreateUserDto user) throws NameAlreadyBoundException;

    UserDto updateUser(Long id, UpdateUserDto user);

    void deleteUser(Long id);
}
