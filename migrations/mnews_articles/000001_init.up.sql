create table users
(
    id       bigserial
        primary key,
    password varchar(1000),
    username varchar(255)
);


create table articles
(
    id             bigserial
        primary key,
    article_date   timestamp(6),
    description_en varchar(2048),
    description_ua varchar(2048),
    link           varchar(255),
    source         varchar(255),
    title_en       varchar(255),
    title_ua       varchar(255),
    user_id        bigint
        constraint fklc3sm3utetrj1sx4v9ahwopnr
            references users
);

create table category
(
    id         bigint not null
        constraint fk4335om405la1e2i4u62c0oiok
            references articles,
    categories varchar(255)
        constraint category_categories_check
            check ((categories)::text = ANY
                   ((ARRAY ['CATEGORY_WAR'::character varying, 'CATEGORY_SPORT'::character varying, 'CATEGORY_ECONOMY'::character varying, 'CATEGORY_ENTERTAINMENT'::character varying, 'CATEGORY_SCIENCE'::character varying, 'CATEGORY_OTHER'::character varying])::text[]))
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

insert into users (id, password, username)
values (1, '$2a$06$Vb6T.hosjM1TTw.iUONIbeFRuSpQK1BpwOz.xmsNRYZTeYdhtBNX2', 'antoha'),
       (2, '$2a$06$Vb6T.hosjM1TTw.iUONIbeFRuSpQK1BpwOz.xmsNRYZTeYdhtBNX2', 'UNIAN'),
       (3, '$2a$06$Vb6T.hosjM1TTw.iUONIbeFRuSpQK1BpwOz.xmsNRYZTeYdhtBNX2', 'FOX'),
       (4, '$2a$06$Vb6T.hosjM1TTw.iUONIbeFRuSpQK1BpwOz.xmsNRYZTeYdhtBNX2', 'CNN'),
       (5, '$2a$06$Vb6T.hosjM1TTw.iUONIbeFRuSpQK1BpwOz.xmsNRYZTeYdhtBNX2', 'PRAVDA');

insert into role (id, roles)
values (1, 'ROLE_ADMIN'),
       (2, 'ROLE_EDITOR'),
       (3, 'ROLE_EDITOR'),
       (4, 'ROLE_EDITOR'),
       (5, 'ROLE_EDITOR');