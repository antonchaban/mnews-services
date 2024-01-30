package com.a.chaban.uservice.userservice.dtos;


import com.a.chaban.uservice.userservice.models.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto implements DTOEntity{
    private Long id;
    private String username;
    private Set<Role> roles = new HashSet<>();
}
