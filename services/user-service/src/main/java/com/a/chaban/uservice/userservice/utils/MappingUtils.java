package com.a.chaban.uservice.userservice.utils;


import com.a.chaban.uservice.userservice.dtos.UserDto;
import com.a.chaban.uservice.userservice.models.User;
import org.springframework.stereotype.Service;

@Service
public class MappingUtils {
    public UserDto mapToUserDto(User entity){
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setRoles(entity.getRoles());
        return dto;
    }

    public User mapToUserEntity(UserDto dto){
        User entity = new User();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setRoles(dto.getRoles());
        return entity;
    }
}
