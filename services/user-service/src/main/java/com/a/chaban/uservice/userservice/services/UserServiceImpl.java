package com.a.chaban.uservice.userservice.services;

import com.a.chaban.uservice.userservice.dtos.CreateUserDto;
import com.a.chaban.uservice.userservice.dtos.UpdateUserDto;
import com.a.chaban.uservice.userservice.dtos.UserDto;
import com.a.chaban.uservice.userservice.models.Role;
import com.a.chaban.uservice.userservice.models.User;
import com.a.chaban.uservice.userservice.repositories.UserRepo;
import com.a.chaban.uservice.userservice.utils.MappingUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.NameAlreadyBoundException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final MappingUtils mappingUtils;
    private final RabbitMQUserProducer producer;


    @Override
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public UserDto findUserById(Long id) {
        return userRepo.findById(id).map(mappingUtils::mapToUserDto).orElse(null);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepo.findAll().stream().map(mappingUtils::mapToUserDto).collect(Collectors.toList());
    }

    @Override
    public User createUser(CreateUserDto user) throws NameAlreadyBoundException {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new NameAlreadyBoundException("Username already taken");
        }
        User userEntity = new User();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.getRoles().add(Role.ROLE_EDITOR);
        userRepo.save(userEntity);
        producer.sendUserEntity(userEntity);
        return userEntity;
    }


    @Override
    public UserDto updateUser(Long id, UpdateUserDto user) {
        User userEntity = userRepo.findById(id).orElse(null);
        if (userEntity == null) {
            return null;
        }
        userEntity.getRoles().clear();
        userEntity.getRoles().addAll(user.getRole());
        userRepo.save(userEntity);
        producer.sendUserEntity(userEntity);
        return mappingUtils.mapToUserDto(userEntity);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
        producer.sendDeleteUser(id);
    }
}
