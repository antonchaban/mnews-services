package com.a.chaban.uservice.userservice.services;

import com.a.chaban.uservice.userservice.dtos.CreateUserDto;
import com.a.chaban.uservice.userservice.dtos.UpdateUserDto;
import com.a.chaban.uservice.userservice.dtos.UserDto;
import com.a.chaban.uservice.userservice.models.Role;
import com.a.chaban.uservice.userservice.models.User;
import com.a.chaban.uservice.userservice.repositories.UserRepo;
import com.a.chaban.uservice.userservice.utils.MappingUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.naming.NameAlreadyBoundException;
import java.util.List;


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

    public List<UserDto> findAll() {
        return userRepo.findAll().stream().map(mappingUtils::mapToUserDto).toList();
    }

    public User createUser(CreateUserDto user) throws NameAlreadyBoundException {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new NameAlreadyBoundException("Username already taken");
        }
        var userEntity = new User();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.getRoles().add(Role.ROLE_EDITOR);
        userRepo.save(userEntity);
        producer.sendUserEntity(userEntity);
        return userEntity;
    }

    public UserDto updateUser(Long id, UpdateUserDto user) {
        var userEntity = userRepo.findById(id).orElse(null);
        if (userEntity == null) {
            return null;
        }
        userEntity.getRoles().clear();
        userEntity.getRoles().addAll(user.getRole());
        userRepo.save(userEntity);
        producer.sendUserEntity(userEntity);
        return mappingUtils.mapToUserDto(userEntity);
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
        producer.sendDeleteUser(id);
    }
}
