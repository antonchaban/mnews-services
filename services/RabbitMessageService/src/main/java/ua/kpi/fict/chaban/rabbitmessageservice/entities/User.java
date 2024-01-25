package ua.kpi.fict.chaban.rabbitmessageservice.entities;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
}
