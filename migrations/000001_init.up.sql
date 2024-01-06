create table users
(
    id serial primary key,
    password    varchar(1000),
    username    varchar(255) not null unique
);


create table articles
(
    id             serial primary key,
    article_date           timestamp,
    description_ua    varchar(2048),
    description_en varchar(2048),
    link           varchar(255),
    source         varchar(255),
    title_ua         varchar(500),
    title_en       varchar(500),
    user_id   bigint
        constraint fk8qj8c8w32ufmvytrlmlkglep5
            references users
);

create table category
(
    id bigint not null
        constraint fkrw5912jiy0vyqoyqlo5r65igk
            references articles,
    categories varchar(255)
);


create table role
(
    id bigint not null
        constraint fkrk3268jfmu796ejtnxt5pa4kt
            references users,
    roles       varchar(255)
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