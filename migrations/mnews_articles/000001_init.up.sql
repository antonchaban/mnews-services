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
                   ((ARRAY ['U_S_NEWS'::character varying, 'COMEDY'::character varying, 'PARENTING'::character varying, 'WORLD_NEWS'::character varying, 'CULTURE_AND_ARTS'::character varying, 'TECH'::character varying, 'SPORTS'::character varying, 'ENTERTAINMENT'::character varying, 'POLITICS'::character varying, 'WEIRD_NEWS'::character varying, 'ENVIRONMENT'::character varying, 'EDUCATION'::character varying, 'CRIME'::character varying, 'SCIENCE'::character varying, 'WELLNESS'::character varying, 'BUSINESS'::character varying, 'STYLE_AND_BEAUTY'::character varying, 'FOOD_AND_DRINK'::character varying, 'MEDIA'::character varying, 'QUEER_VOICES'::character varying, 'HOME_AND_LIVING'::character varying, 'WOMEN'::character varying, 'BLACK_VOICES'::character varying, 'GEOPOLITICS'::character varying, 'MONEY'::character varying, 'RELIGION'::character varying, 'LATINO_VOICES'::character varying, 'IMPACT'::character varying, 'WEDDINGS'::character varying, 'COLLEGE'::character varying, 'PARENTS'::character varying, 'ARTS_AND_CULTURE'::character varying, 'STYLE'::character varying, 'GREEN'::character varying, 'TASTE'::character varying, 'HEALTHY_LIVING'::character varying, 'THE_WORLDPOST'::character varying, 'GOOD_NEWS'::character varying, 'WORLDPOST'::character varying, 'FIFTY'::character varying, 'ARTS'::character varying, 'DIVORCE'::character varying])::text[]))
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