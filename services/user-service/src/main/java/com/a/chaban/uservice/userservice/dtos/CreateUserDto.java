package com.a.chaban.uservice.userservice.dtos;


import lombok.Data;

@Data
public class CreateUserDto implements DTOEntity {
    private String username;
    private String password;
}
