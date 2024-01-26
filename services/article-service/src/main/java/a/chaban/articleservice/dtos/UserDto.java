package a.chaban.articleservice.dtos;


import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import a.chaban.articleservice.models.Role;

@Data
public class UserDto {
    private Long id;
    private String username;
    private Set<Role> roles = new HashSet<>();
}
