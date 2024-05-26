package com.a.chaban.uservice.userservice.dtos;

import com.a.chaban.uservice.userservice.models.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UpdateUserDto implements DTOEntity {
    private Long id;
    private Set<Role> role = new HashSet<>();
}
