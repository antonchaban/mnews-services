create table users
(
    id       bigserial
        primary key,
    password varchar(2048),
    username varchar(255)
);

create table role
(
    id    bigint not null
        constraint fkmkvuwweyisdgw5vulplv5aven
            references users,
    roles varchar(255)
        constraint role_roles_check
            check ((roles)::text = ANY
                   ((ARRAY ['ROLE_EDITOR'::character varying, 'ROLE_ADMIN'::character varying])::text[]))
);

insert into users (password, username)
values ('$2a$06$Vb6T.hosjM1TTw.iUONIbeFRuSpQK1BpwOz.xmsNRYZTeYdhtBNX2', 'antoha'),
       ('$2a$06$Vb6T.hosjM1TTw.iUONIbeFRuSpQK1BpwOz.xmsNRYZTeYdhtBNX2', 'UNIAN'),
       ('$2a$06$Vb6T.hosjM1TTw.iUONIbeFRuSpQK1BpwOz.xmsNRYZTeYdhtBNX2', 'FOX'),
       ('$2a$06$Vb6T.hosjM1TTw.iUONIbeFRuSpQK1BpwOz.xmsNRYZTeYdhtBNX2', 'CNN'),
       ('$2a$06$Vb6T.hosjM1TTw.iUONIbeFRuSpQK1BpwOz.xmsNRYZTeYdhtBNX2', 'PRAVDA');

insert into role (id, roles)
values (1, 'ROLE_ADMIN'),
       (2, 'ROLE_EDITOR'),
       (3, 'ROLE_EDITOR'),
       (4, 'ROLE_EDITOR'),
       (5, 'ROLE_EDITOR');