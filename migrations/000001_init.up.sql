create table users
(
    id serial primary key,
    password    varchar(1000),
    username    varchar(255) not null unique
);

alter table users
    owner to postgres;

create table article
(
    article_id             serial primary key,
    article_date           timestamp,
    article_description    varchar(2048),
    article_description_en varchar(2048),
    article_link           varchar(255),
    article_source         varchar(255),
    article_title          varchar(500),
    article_title_en       varchar(500),
    customer_customer_id   bigint
        constraint fk8qj8c8w32ufmvytrlmlkglep5
            references users
);

alter table article
    owner to postgres;

create table article_category
(
    article_id bigint not null
        constraint fkrw5912jiy0vyqoyqlo5r65igk
            references article,
    categories varchar(255)
);

alter table article_category
    owner to postgres;

create table role
(
    id bigint not null
        constraint fkrk3268jfmu796ejtnxt5pa4kt
            references users,
    roles       varchar(255)
);

alter table role
    owner to postgres;

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